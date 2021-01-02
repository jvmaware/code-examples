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
 * @author gaurs
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
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
