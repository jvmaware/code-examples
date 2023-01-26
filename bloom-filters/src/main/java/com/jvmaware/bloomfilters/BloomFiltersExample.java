package com.jvmaware.bloomfilters;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * A quick example to showcase the usage of BloomFilters as
 * provided by Guava library.
 *
 * @author gaurs
 */
public class BloomFiltersExample{

    /**
     * Creates a bloom filter using the static method os same name
     * from {@link BloomFilter>}
     *
     * @param size                          expected number of elements
     * @param acceptableFalsePositivityRate acceptable false positivity rate
     * @return BF instance
     */
    public BloomFilter<Integer> create(int size, double acceptableFalsePositivityRate){
        return BloomFilter.create(Funnels.integerFunnel(), size, acceptableFalsePositivityRate);
    }

    /**
     * A utility method to add an element in BloomFilter
     *
     * @param input       to be added
     * @param bloomFilter BF instance
     * @return put result
     */
    public boolean add(int input, BloomFilter<Integer> bloomFilter){
        return bloomFilter.put(input);
    }

    /**
     * A probable ans for presence of an element.
     *
     * @param input       to be checked
     * @param bloomFilter BF instance
     * @return false if element is missing; true may or may not present
     */
    public boolean checkPresence(int input, BloomFilter<Integer> bloomFilter){
        return bloomFilter.mightContain(input);
    }
}