package com.jvmaware.ctp


import spock.lang.Specification

import java.util.concurrent.Callable

class CustomThreadPoolImplSpec extends Specification {

    def "test the creation of thread-pool with default values"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()

        when: "a thread pool is built with default values"
        def threadPool = builder.build()

        then: "it should have a single thread"
        threadPool.threadList.size() == 1
        and: "it should not have any tasks"
        threadPool.taskList.size() == 0
    }

    def "test the successful submission of tasks"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with default values which will not size-up for pending tasks"
        def threadPool = builder.build()

        when: "a task is submitted for execution which will take some time to complete"
        threadPool.submit(() -> {
            println("sleeping the thread to allow other tasks to be in pending thread allocation state")
            Thread.sleep(2000)
        })
        and: "another task is submitted for execution. As the previous task is still running, it will be waiting for thread allocation"
        threadPool.submit(() -> println("this is second task"))

        then: "threadPool should have a single thread as corePoolSize == maxPoolSize"
        threadPool.threadList.size() == 1
        and: "taskList should have a pending task as the first task is a long running task"
        threadPool.taskList.size() == 1
    }

    def "test the scale up of thread-pool in case more tasks than corePoolSize are submitted"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(() -> {
            println("sleeping the thread to force new thread creation as corePoolSize < maxPoolSize")
            Thread.sleep(5000)
        })
        and: "another task is submitted for execution"
        threadPool.submit(() -> {
            println("this is second task; it will also sleep for sometime to force next submitted task to be in pending state")
            Thread.sleep(5000)
        })
        and: "another task is submitted for execution; this should be pending"
        threadPool.submit(() -> println("this is third task"))

        then: "it should create a new thread for the second task when first task is still running"
        threadPool.threadList.size() == 2
        then: "third task should be in pending state as maxPoolSize is 2"
        threadPool.taskList.size() == 1
    }

    def "test isShutdown method returns correct status for shutdown_ed thread-pool"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "thread-pool is shutdown"
        threadPool.shutdown()

        then: "correct status should be returned"
        threadPool.isShutdown()
    }

    def "test isShutdown method returns correct status for running thread-pool"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()

        when: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        then: "correct status should be returned"
        !threadPool.isShutdown()
    }

    def "test submitting a runnable task after shutdown is triggered should result in exception"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(() -> {
            println("sleeping the thread to simulate request processing")
            Thread.sleep(5000)
        })
        and: "thread-pool is shutdown"
        threadPool.shutdown()
        and: "a new task is submitted post shutdown trigger"
        threadPool.submit(() -> "this should cause an exception")

        then: "submitting a new task should throw an exception"
        thrown(RuntimeException)
    }

    def "test submitting a callable task after shutdown is triggered should result in exception"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(new Callable<String>() {
            @Override
            String call() throws Exception {
                println("sleeping the thread to simulate request processing")
                Thread.sleep(5000)
                return "Done"
            }
        })
        and: "thread-pool is shutdown"
        threadPool.shutdown()
        and: "a new task is submitted post shutdown trigger"
        threadPool.submit(new Callable<String>() {
            @Override
            String call() throws Exception {
                println("this should cause an exception")
                return "Oops"
            }
        })

        then: "submitting a new task should throw an exception"
        thrown(RuntimeException)
    }

    def "test pending tasks should be returned when thread-pool is shutdown"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(() -> {
            println("sleeping the thread to simulate request processing")
            Thread.sleep(5000)
        })
        and: "another task is submitted for processing"
        threadPool.submit(() -> "this should cause an exception")
        and: "thread-pool is shutdown"
        def pendingTasks = threadPool.shutdownNow()

        then: "pending tasks should contains un-processed tasks"
        pendingTasks.size() == 1
    }

    def "test await termination without invoking shutdown"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(() -> {
            println("sleeping the thread to simulate request processing")
            Thread.sleep(5000)
        })
        and: "wait for termination"
        threadPool.awaitTermination(1000)

        then: "an exception should be thrown"
        thrown(RuntimeException)
    }

    def "test await termination when waitTime is more than processing time"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(() -> {
            println("sleeping the thread to simulate request processing")
            Thread.sleep(5000)
        })
        and: "thread-pool is shutdown"
        threadPool.shutdown()
        and: "wait for termination for more than 5000 milliseconds"
        def status = threadPool.awaitTermination(5500)

        then: "all tasks should have completed successfully"
        status
    }

    def "test await termination when waitTime is less than processing time"(){
        given: "a thread pool builder"
        def builder = CustomThreadPoolImpl.CustomThreadPoolBuilder.aCustomThreadPool()
        and: "a thread pool is built with corePoolSize and maxPoolSize values set (corePoolSize < maxPoolSize)"
        def threadPool = builder.withCorePoolSize(1).withMaxPoolSize(2).build()

        when: "a task is submitted for execution"
        threadPool.submit(() -> {
            println("sleeping the thread to simulate request processing")
            Thread.sleep(5000)
        })
        and: "thread-pool is shutdown"
        threadPool.shutdown()
        and: "wait for termination for less than 5000 milliseconds"
        def status = threadPool.awaitTermination(1000)

        then: "all tasks should NOT have completed successfully"
        !status
    }
}
