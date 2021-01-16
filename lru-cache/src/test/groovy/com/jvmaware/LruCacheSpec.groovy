package com.jvmaware

import spock.lang.Specification

class LruCacheSpec extends Specification{

    def "test the fixed size cache does not exceed its pre-defined size"(){
        given: "the cache is initialized with a fixed size"
        def size = 5
        LruCache<String, Integer> cache = new LruCache<>(size)

        when: "elements more than the predefined size are added"
        cache.put("key1", 1)
        cache.put("key2", 2)
        cache.put("key3", 3)
        cache.put("key4", 4)
        cache.put("key5", 5)
        cache.put("key6", 6)

        then: "the cache does not grow past the max allowed size"
        cache.size() == size
    }

    def "when not accessed, the elements are present in the order of insertion"(){
        given: "the cache is initialized with a fixed size"
        def size = 5
        LruCache<String, Integer> cache = new LruCache<>(size)

        when: "elements are added"
        cache.put("key1", 1)
        cache.put("key2", 2)
        cache.put("key3", 3)
        cache.put("key4", 4)
        cache.put("key5", 5)

        then: "the cache does not grow past the max allowed size"
        cache.values()[0] == 1
        cache.values()[1] == 2
        cache.values()[2] == 3
        cache.values()[3] == 4
        cache.values()[4] == 5
    }

    def "the most recently accessed element is pushed to the end of the cache"(){
        given: "the cache is initialized with a fixed size"
        def size = 5
        LruCache<String, Integer> cache = new LruCache<>(size)
        and: "elements are added"
        cache.put("key1", 1)
        cache.put("key2", 2)
        cache.put("key3", 3)
        cache.put("key4", 4)
        cache.put("key5", 5)

        when: "an element with key: key1 is accessed"
        cache.get("key1")
        cache.get("key2")

        then: "the element with key: key1 is now pushed to the end of the cache"
        cache.values()[0] == 3
        cache.values()[1] == 4
        cache.values()[2] == 5
        cache.values()[3] == 1
        cache.values()[4] == 2
    }

    def "the least recently accessed is kicked out in-case of an overflow"(){
        given: "the cache is initialized with a fixed size"
        def size = 5
        LruCache<String, Integer> cache = new LruCache<>(size)
        and: "elements are added"
        cache.put("key1", 1)
        cache.put("key2", 2)
        cache.put("key3", 3)
        cache.put("key4", 4)
        cache.put("key5", 5)

        when: "two elements are accessed"
        cache.get("key1")
        cache.get("key2")
        and: "a new element is added that will cause an overflow"
        cache.put("key6", 6)

        then: "the element with key: key3 is evicted as it in the front of the queue"
        cache.get("key3") == null
    }
}
