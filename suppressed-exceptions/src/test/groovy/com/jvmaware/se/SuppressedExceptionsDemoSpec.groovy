package com.jvmaware.se

import spock.lang.Specification

class SuppressedExceptionsDemoSpec extends Specification {

    def suppressedExceptionsDemo = new SuppressedExceptionsDemo();

    def "Test in case of multiple exceptions, the one thrown from finally reaches the client"(){
        given: "Context is loaded"
        when: "an exception from both try and finally is thrown"

        suppressedExceptionsDemo.exceptionBearingAction("some-random-path")
        then: "exception is received"
        thrown(NullPointerException.class)
    }

    def "Test the presence of a suppressed exception"(){
        given: "Context is loaded"
        when: "an exception from both try and finally is thrown"

        suppressedExceptionsDemo.suppressedExceptionBearingAction("some-random-path")
        then: "exception is received"
        def npe = thrown(NullPointerException.class)
        and: "exception thrown from finally block contains a suppressed exception"
        npe.getSuppressed().length > 0
        and: "the suppressed exception is of type FileNotFoundException, i.e. the one thrown from try block"
        FileNotFoundException.class == npe.getSuppressed()[0].class
    }
}
