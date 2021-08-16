package com.jvmaware.ctp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A {@link CustomThreadPool} that executes each submitted task using one
 * of possibly several pooled threads.
 *
 * @author gaurs
 */

@SuppressWarnings("unused")
public class CustomThreadPoolImpl implements CustomThreadPool {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final int keepAlive;
    private final AtomicBoolean run;

    private final BlockingQueue<Runnable> taskList;
    private final List<Thread> threadList;
    private final Supplier<Thread> threadSupplier;

    private CustomThreadPoolImpl(int corePoolSize, int maxPoolSize, int keepAlive) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAlive = keepAlive;
        this.run = new AtomicBoolean(true);

        // unbounded
        taskList = new LinkedBlockingQueue<>();
        threadList = new ArrayList<>(corePoolSize);
        threadSupplier = () -> new CustomThread(this.taskList, this.run, this.corePoolSize, this.keepAlive);

        init();
    }

    /**
     * The method is used to initialize the threads in the <code>threadList</code> so that
     * they can start monitoring the <code>taskList</code> for the availability of new
     * tasks.
     */
    private void init() {
        Stream.generate(threadSupplier).limit(corePoolSize).forEach(thread -> {
            threadList.add(thread);
            thread.start();
        });
    }

    @Override
    public void shutdown() {
        this.run.set(false);
    }

    @Override
    public List<Runnable> shutdownNow() {
        // do not accept any more tasks
        // notify threads to stop monitoring taskList
        shutdown();

        List<Runnable> pendingTasks = new ArrayList<>(taskList.size());

        // clear any un-submitted task
        if (!taskList.isEmpty()) {
            taskList.drainTo(pendingTasks);
        }

        // try to stop any running task
        for (Thread customThread : threadList) {
            customThread.interrupt();
        }

        return pendingTasks;
    }

    @Override
    public boolean isShutdown() {
        // negate the run flag value
        return !this.run.get();
    }

    @Override
    public boolean awaitTermination(long timeoutInMilli) throws InterruptedException {
        if (this.run.get()) {
            throw new IllegalStateException("threadPool need to be shutdown first before awaitTermination is called");
        }
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= timeoutInMilli) {
            Optional<Thread> aliveThread = threadList.stream().filter(Thread::isAlive).findAny();
            if(aliveThread.isEmpty()){
                return true;
            }
            Thread.onSpinWait();
        }
        // timeout reached but tasks are still not complete
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (run.get()) {
            RunnableFuture<T> future = new FutureTask<>(task);
            handle(future);
            return future;
        }

        throw new RuntimeException("new tasks cannot be submitted after shutdown is called");
    }

    @Override
    public Future<?> submit(Runnable task) {
        if (run.get()) {
            RunnableFuture<?> future = new FutureTask<>(task, null);
            handle(future);
            return future;
        }
        throw new RuntimeException("new tasks cannot be submitted after shutdown is called");
    }

    private void handle(RunnableFuture<?> futureTask){
        if(taskList.size() < corePoolSize){
            taskList.add(futureTask);
        }else if(taskList.size() < maxPoolSize){
            // create a new thread for the threadList
            CustomThread customThread = new CustomThread(taskList, run, corePoolSize, keepAlive);
            threadList.add(customThread);
            customThread.start();

            // submit the task
            taskList.add(futureTask);
        }else{
            // maxPool size already reached
            // simply submit the task
            taskList.add(futureTask);
        }
    }

    /**
     * A custom thread class with the capability to monitor the <code>taskList</code>
     * for the availability of new tasks.
     */
    private static final class CustomThread extends Thread {
        private final BlockingQueue<Runnable> taskList;
        private final AtomicBoolean run;

        private CustomThread(BlockingQueue<Runnable> taskList, AtomicBoolean run, int corePoolSize, int keepAlive) {
            this.taskList = taskList;
            this.run = run;
        }

        @Override
        public void run() {
            try {
                while (run.get()) {
                    // this will wait until a task is available
                    taskList.take().run();
                }
            } catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException);
            }
        }
    }

    /**
     * A static builder class to instantiate CustomThreadPool instance with either the
     * supplied configurations or with default values.
     */
    public static final class CustomThreadPoolBuilder {
        private int corePoolSize = 1;
        private int maxPoolSize = 1;
        private int keepAlive = -1;

        private CustomThreadPoolBuilder() {
        }

        public static CustomThreadPoolBuilder aCustomThreadPool() {
            return new CustomThreadPoolBuilder();
        }

        /**
         * The minimum number of threads the pool will have at any given point of time.
         * This should be application specific as too low count will increase the thread lifecycle
         * related activities, and too high might waste some unused threads.
         * <p>
         * If un-supplied, the default value is 1.
         *
         * @param corePoolSize the core pool size
         * @return the builder
         */
        public CustomThreadPoolBuilder withCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        /**
         * The maximum number of threads allowed in the pool. It allows the pool to grow
         * beyond the core pool size to accommodate new tasks.
         * <p>
         * If un-supplied, the default value is 1.
         *
         * @param maxPoolSize maximum number of allowed threads
         * @return the builder
         */
        public CustomThreadPoolBuilder withMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        /**
         * Maximum time in milliseconds for which an <strong>idle</strong>thread will wait before dying.
         * This ensures to downsize the threadPool to corePoolSize, in case it was sized up
         * to maxPoolSize for accommodating new tasks.
         * <p>
         * If un-supplied, the default value is -1 i.e. never terminate those threads.
         *
         * @param keepAlive keep alive time for idle threads in milliseconds
         * @return the builder
         */
        public CustomThreadPoolBuilder withKeepAlive(int keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        /**
         * Build the thread pool with the supplied params or default values.
         *
         * @return {@link CustomThreadPool} instance
         */
        public CustomThreadPoolImpl build() {
            return new CustomThreadPoolImpl(corePoolSize, maxPoolSize, keepAlive);
        }
    }
}
