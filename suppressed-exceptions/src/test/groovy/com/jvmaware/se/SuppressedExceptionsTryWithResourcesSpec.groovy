package com.jvmaware.se

import spock.lang.Specification

class SuppressedExceptionsTryWithResourcesSpec extends Specification {
    def suppressedExceptionsDemo = new SuppressedExceptionsTryWithResources()

    def "Test in case of multiple exceptions, the one thrown from try reaches the client"(){
        given: "Context is loaded"
        when: "an exception from both try and implicit finally is thrown"

        suppressedExceptionsDemo.tryReadingFile("some-random-path")
        then: "exception is received"
        thrown(FileNotFoundException.class)
    }
}
