package com.jvmaware

import spock.lang.Specification

class CustomFutureTaskSpec extends Specification{

    def "test if the comparator works as expected for a CustomFutureTask" (){
        given: "tasks are created and assigned priority"
        Task taskFirst = new Task("some task", 10)
        Task taskSecond = new Task("some other task", 1)

        when: "custom future tasks are created for those"
        CustomFutureTask cftOne = new CustomFutureTask(taskFirst)
        CustomFutureTask cftSecond = new CustomFutureTask(taskSecond)

        then: "the custom tasks should be comparable"
        cftOne instanceof Comparable
        cftSecond instanceof Comparable
        and: "the tasks should be compared based on the priority"
        cftSecond < cftOne
    }
}
