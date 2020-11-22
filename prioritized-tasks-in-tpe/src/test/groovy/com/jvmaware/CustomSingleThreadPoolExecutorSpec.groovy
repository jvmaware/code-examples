package com.jvmaware

import spock.lang.Specification
import spock.mock.DetachedMockFactory
import spock.mock.MockFactory

import java.util.concurrent.PriorityBlockingQueue

class CustomSingleThreadPoolExecutorSpec extends Specification{

    def DetachedMockFactory mockFactory = new DetachedMockFactory()

    def "test the newTaskFor method returns a Comparable FutureTask"(){
        given: "given a priority blocking queue"
        PriorityBlockingQueue<Runnable> pbq = new PriorityBlockingQueue<>()
        and: "CTPE is created using the PriorityBlockingQueue"
        CustomSingleThreadPoolExecutor customSingleThreadPoolExecutor = new CustomSingleThreadPoolExecutor(pbq)


        when: "overridden newTaskFor method is called"
        def wrappedTask = customSingleThreadPoolExecutor.newTaskFor(new Task("some task", 10), null)

        then: "wrappedTask should be Comparable"
        wrappedTask instanceof Comparable
    }

    def "run method is invoked on the wrapped task"(){
        given: "given a priority blocking queue and a task"
        PriorityBlockingQueue<Runnable> pbq = new PriorityBlockingQueue<>()
        Task task = new Task("some task", 1)
        and: "CTPE is created using the PriorityBlockingQueue"
        CustomSingleThreadPoolExecutor customSingleThreadPoolExecutor = new CustomSingleThreadPoolExecutor(pbq)

        when: "a task is submitted"
        customSingleThreadPoolExecutor.submit(task)
        Thread.sleep(3000) // wait for the task to complete and before invoking then

        then: "run method is called"
        task.getResult().size() == 1
    }

    def "runtime exception is thrown if thread is interrupted"(){
        given: "given a priority blocking queue and a task"
        PriorityBlockingQueue<Runnable> pbq = new PriorityBlockingQueue<>()
        Task task = new Task("some task", 1)
        and: "CTPE is created using the PriorityBlockingQueue"
        CustomSingleThreadPoolExecutor customSingleThreadPoolExecutor = new CustomSingleThreadPoolExecutor(pbq)

        when: "a task is submitted"
        customSingleThreadPoolExecutor.submit(task)
        customSingleThreadPoolExecutor.shutdownNow()

        then: "thread is interrupted"
        task.getResult().size() == 0
    }
}
