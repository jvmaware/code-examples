package com.jvmaware.se

import spock.lang.Specification

class SuppressedExceptionsTryWithResourcesSpec extends Specification {
    def suppressedExceptionsDemo = new SuppressedExceptionsTryWithResources()

    def "Test in case of multiple exceptions, the one thrown from try reaches the client"(){
        given: "Context is loaded"
        when: "an exception from both try and implicit finally is thrown"

        suppressedExceptionsDemo.tryReadingFile("some-random-path")
        then: "exception is received"
        def fnf = thrown(FileNotFoundException.class)
        and: "suppressed exceptions is present"
        fnf.getSuppressed().length != 0
        and: "the exception thrown from the close method is added to the suppressed list"
        fnf.getSuppressed()[0].class == RuntimeException.class
        and: "check the message"
        fnf.getSuppressed()[0].message == "SomeAutoCloseableClass: Exception thrown from Implicit finally block while closing"
    }
}
