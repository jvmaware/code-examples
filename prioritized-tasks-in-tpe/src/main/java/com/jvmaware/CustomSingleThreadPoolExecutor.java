package com.jvmaware;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The Custom ThreadPoolExecutor that overrides the newTaskFor method to
 * return a comparable Future task. Various configurations for instantiating {@link ThreadPoolExecutor}
 * like <code>corePoolSize</code>, <code>maximumPoolSize</code> etc. are set to default values for now.
 * These can be read from a configuration file as well.
 *
 * @author gaurs
 */
class CustomSingleThreadPoolExecutor extends ThreadPoolExecutor {
    public CustomSingleThreadPoolExecutor(BlockingQueue<Runnable> workQueue) {
        super(1, 1, 1000, TimeUnit.SECONDS, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new CustomFutureTask<>(runnable);
    }
}