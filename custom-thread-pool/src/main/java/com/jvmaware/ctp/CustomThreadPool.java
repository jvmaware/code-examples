package com.jvmaware.ctp;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The interface represents a custom thread pool similar to many alternatives provided
 * by java like {@link java.util.concurrent.ThreadPoolExecutor} or implementations of
 * {@link java.util.concurrent.ExecutorService}
 * <p>
 * The contract and its implementation {@link CustomThreadPoolImpl} is written as an example for the
 * <a href="https://jvmaware.com/custom-thread-pool/">blog</a> and is no where production ready.
 * It might also lack some basic features as the idea is to just describe the internal structure of
 * such an API provided by java language.
 * <p>
 * The idea is to create a number of threads (depending on construction params) and
 * make those available for accepting new tasks to be run asynchronously. This allows
 * us to re-use any thread which has already completed its corresponding task reducing
 * the overall number of threads created.
 *
 * @author gaurs
 */
public interface CustomThreadPool {

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new
     * tasks will be accepted.
     * <p>
     * Invocation has no additional effect if already shut down. This method does not wait for previously
     * submitted tasks to complete execution. Use awaitTermination to do that.
     */
    void shutdown();

    /**
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks,
     * and returns a list of the tasks that were awaiting execution.
     * <p>
     * There are no guarantees beyond best-effort attempts to stop processing actively executing tasks.
     * For example, typical implementations will cancel via <code>Thread.interrupt</code>, so any task
     * that fails to respond to interrupts may never terminate.
     *
     * @return list of tasks that never commenced execution
     */
    List<Runnable> shutdownNow();

    /**
     * Returns true if this executor has been shut down.
     *
     * @return true if this executor has been shut down.
     */
    boolean isShutdown();

    /**
     * Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs,
     * or the current thread is interrupted, whichever happens first.
     *
     * @param timeoutInMilli the maximum time in milliseconds to wait
     * @return true if this executor terminated and false if the timeout elapsed before termination
     * @throws InterruptedException if interrupted while waiting
     */
    boolean awaitTermination(long timeoutInMilli) throws InterruptedException, TimeoutException;

    /**
     * Submits a value-returning task for execution and returns a Future representing the pending results
     * of the task. The Future's {@code get} method will return the task's result upon successful completion.
     *
     * @param task the task to submit
     * @param <T>  the type of the task's result
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be scheduled for execution
     * @throws NullPointerException       if the task is null
     */
    <T> Future<T> submit(Callable<T> task);

    /**
     * Submits a Runnable task for execution and returns a Future representing that task.
     * The Future's {@code get} method will return {@code null} upon <em>successful</em>
     * completion.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be scheduled for execution
     * @throws NullPointerException       if the task is null
     */
    Future<?> submit(Runnable task);
}
