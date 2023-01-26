package com.jvmaware.bloomfilters


import spock.lang.Specification

import java.util.stream.IntStream

class BloomFiltersExampleSpec extends Specification{

    def "test creating a bloom filter"(){
        given: "an upper bound n for the total number of elements"
        def n = 10
        and: "an acceptable false positivity rate"
        double fpr = 0.01
        var bfExample = new BloomFiltersExample()

        when: "the BF instance is created"
        def instance = bfExample.create(n, fpr)

        then: "a valid instance is created"
        instance != null
        and: "instance is empty"
        instance.approximateElementCount() == 0
        and: "FPP is less than equal to FPR"
        instance.expectedFpp() <= fpr
    }

    def "test addition of elements less than the upper limit"(){
        given: "an upper bound n for the total number of elements"
        def n = 10
        and: "an acceptable false positivity rate"
        double fpr = 0.01
        var bfExample = new BloomFiltersExample()

        when: "the BF instance is created and elements are added"
        def instance = bfExample.create(n, fpr)
        IntStream.range(0, 7).forEach (val -> bfExample.add(val, instance))

        then: "a valid instance is created"
        instance != null
        and: "instance is NOT empty"
        instance.approximateElementCount() == 7
        and: "FPP is less than equal to FPR"
        instance.expectedFpp() <= fpr
    }

    def "test presence of elements when added element count less than the upper limit"() {
        given: "an upper bound n for the total number of elements"
        def n = 10
        and: "an acceptable false positivity rate"
        double fpr = 0.01
        var bfExample = new BloomFiltersExample()

        when: "the BF instance is created and elements are added"
        def instance = bfExample.create(n, fpr)
        IntStream.range(0, 7).forEach(val -> bfExample.add(val, instance))

        then: "a valid instance is created"
        instance != null
        and: "instance is NOT empty"
        instance.approximateElementCount() == 7

        and: "no false positives are returned"
        bfExample.checkPresence(entry, instance) == isPresent

        where:
        entry | isPresent
        0     | true
        1     | true
        2     | true
        3     | true
        4     | true
        5     | true
        6     | true
        11    | false
        12    | false
        13    | false
        14    | false
        15    | false
    }

    def "test addition of elements more than the upper limit"(){
        given: "an upper bound n for the total number of elements"
        def n = 10
        and: "an acceptable false positivity rate"
        double fpr = 0.01
        var bfExample = new BloomFiltersExample()

        when: "the BF instance is created and elements are added"
        def instance = bfExample.create(n, fpr)
        IntStream.range(0, 50).forEach (val -> bfExample.add(val, instance))

        then: "a valid instance is created"
        instance != null
        and: "instance is NOT empty"
        instance.approximateElementCount() != 0
        and: "FPP is more than equal to FPR"
        instance.expectedFpp() > fpr
    }

    def "test presence of elements when added element count more than the upper limit"() {
        given: "an upper bound n for the total number of elements"
        def n = 10
        and: "an acceptable false positivity rate"
        double fpr = 0.01
        var bfExample = new BloomFiltersExample()

        when: "the BF instance is created and elements are added"
        def instance = bfExample.create(n, fpr)
        IntStream.range(0, 50).forEach (val -> bfExample.add(val, instance))

        then: "a valid instance is created"
        instance != null
        and: "instance is NOT empty"
        instance.approximateElementCount() != 0

        and: "no false positives are returned"
        bfExample.checkPresence(entry, instance) == isPresent

        where:
        entry | isPresent
        0     | true
        1     | true
        2     | true
        3     | true
        4     | true
        5     | true
        6     | true

        11    | true
        12    | true
        13    | true
        14    | true
        15    | true
    }
}
