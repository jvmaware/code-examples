package com.jvmaware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A Runnable class that denotes a prioritized task that is expected
 * to run inside a TPE following a sequence based on the priority.
 *
 * @author gaurs
 */
class Task implements Runnable {
    private final String name;
    private final int priority;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Long> result;

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.result = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            logger.info("[{}] triggered successfully", this.name);
            Thread.sleep(2000);
            logger.info("[{}] completed successfully", this.name);
            result.add(Instant.now().toEpochMilli());
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(interruptedException);
        }
    }

    public int getPriority() {
        return priority;
    }

    public List<Long> getResult() {
        return result;
    }
}