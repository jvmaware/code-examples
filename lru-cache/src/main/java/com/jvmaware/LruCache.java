package com.jvmaware;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The class represents an LRU - least recent used cache which is an in-memory
 * cache of fixed size. The cache (when full) allocates space to new members by
 * removing existing elements in the reverse order of their usage.
 * <p>
 * The element which was used the most earliest will be evicted first.
 *
 * @author gaurs
 */
public class LruCache<K, V> extends LinkedHashMap<K, V> {

    private final int MAX_ALLOWED_SIZE;
    private static final float LOAD_FACTOR = 0.75f;

    public LruCache(int maxAllowedSize) {
        super(maxAllowedSize, LOAD_FACTOR, true);
        MAX_ALLOWED_SIZE = maxAllowedSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.MAX_ALLOWED_SIZE;
    }
}
