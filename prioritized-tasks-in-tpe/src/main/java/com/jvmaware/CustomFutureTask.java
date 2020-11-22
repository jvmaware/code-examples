package com.jvmaware;

import java.util.concurrent.FutureTask;

/**
 * A FutureTask customization that is also Comparable.
 *
 * @param <T> the result type
 * @author gaurs
 */
class CustomFutureTask<T> extends FutureTask<T> implements Comparable<CustomFutureTask<T>> {

    private final Task task;

    public CustomFutureTask(Runnable task) {
        super(task, null);
        this.task = (Task) task;
    }

    @Override
    public int compareTo(CustomFutureTask that) {
        return Integer.compare(this.task.getPriority(), that.task.getPriority());
    }
}