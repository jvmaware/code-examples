package com.jvmaware.pttp;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The main runner class that will instantiate the {@link CustomSingleThreadPoolExecutor} and will
 * submit prioritized tasks to it.
 *
 * @author gaurs
 */
public class Runner {
    public static void main(String[] args) {
        ThreadPoolExecutor tpe = new CustomSingleThreadPoolExecutor(new PriorityBlockingQueue<>());
        tpe.submit(new Task("T5", 5));
        tpe.submit(new Task("T4", 4));
        tpe.submit(new Task("T3", 3));
        tpe.submit(new Task("T2", 2));
        tpe.submit(new Task("T1", 1));

        tpe.shutdown();
    }
}
