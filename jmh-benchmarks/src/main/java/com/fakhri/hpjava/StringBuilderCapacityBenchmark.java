package com.fakhri.hpjava;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Thread)
public class StringBuilderCapacityBenchmark {

    @Param({"10", "100", "1000"})
    public int length;

    @Benchmark
    public void builderWithoutCapacity(Blackhole bh) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("x");
        }
        bh.consume(sb.toString());
    }

    @Benchmark
    public void builderWithCapacity(Blackhole bh) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append("x");
        }
        bh.consume(sb.toString());
    }

}
