package com.fakhri.jmm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Group)
public class ImprovedCounterBenchmark {

    long syncCounter = 0;

    @Group("sync")
    @Benchmark
    @GroupThreads(32)
    public void syncInc(Blackhole bh) {
        synchronized (this) {
            bh.consume(++syncCounter);
        }
    }

    @State(Scope.Group)
    public static class AtomicState {
        AtomicLong counter = new AtomicLong();
    }

    @Group("atomic")
    @Benchmark
    @GroupThreads(32)
    public void atomicInc(AtomicState state, Blackhole bh) {
        bh.consume(state.counter.incrementAndGet());
    }

    @State(Scope.Group)
    public static class LongAdderState {
        LongAdder adder = new LongAdder();
    }

    @Group("adder")
    @Benchmark
    @GroupThreads(32)
    public void longAdderInc(LongAdderState state, Blackhole bh) {
        state.adder.increment();
        bh.consume(state.adder.sum());
    }
}

