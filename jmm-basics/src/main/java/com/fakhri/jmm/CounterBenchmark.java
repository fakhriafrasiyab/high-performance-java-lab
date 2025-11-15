package com.fakhri.jmm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(8)
public class CounterBenchmark {


    private long syncCounter = 0;
    private volatile long volatileCounter = 0;
    private final AtomicLong atomicCounter = new AtomicLong();
    private final LongAdder longAdder = new LongAdder();


    @Benchmark
    public void synchronizedIncrement(Blackhole bh) {
        synchronized (this) {
            syncCounter++;
            bh.consume(syncCounter);
        }
    }

    @Benchmark
    public void volatileIncrement(Blackhole bh) {
        volatileCounter++; // NOT atomic, only visibility
        bh.consume(volatileCounter);
    }

    @Benchmark
    public void atomicIncrement(Blackhole bh) {
        bh.consume(atomicCounter.incrementAndGet());
    }

    @Benchmark
    public void longAdderIncrement(Blackhole bh) {
        longAdder.increment();
        bh.consume(longAdder.sum());
    }

}
