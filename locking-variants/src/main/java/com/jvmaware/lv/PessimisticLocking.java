package com.jvmaware.lv;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Pessimistic Locking: Pessimistic locking (generally implemented using synchronized keyword in java) is a way of achieving mutual exclusion by
 * always locking the entire scope.The first thread to acquire the lock will retain the lock until the scope execution. It will then release the
 * lock which can then be acquired by any other waiting thread.
 *
 * The problem with this approach lies in the fact that we always assume that all possible threads will be competing for the underlying resource at
 * the same time which is generally not the case.
 *
 *
 * @author gaurs
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Threads(value = Threads.MAX)
public class PessimisticLocking {

    @State(Scope.Benchmark)
    public static class CustomState {
        private volatile int sharedResource;

        public synchronized void inc(){
            sharedResource++;
        }

        public int getSharedResource() {
            return sharedResource;
        }
    }

    @Benchmark
    public void increment(CustomState state, Blackhole blackhole) {
        // All benchmark threads will call in this method.
        // Since BenchmarkState is the Scope.Benchmark, all threads
        // will share the state instance, and we will end up measuring
        // shared case.
        state.inc();
        blackhole.consume(state.getSharedResource());
    }
}
