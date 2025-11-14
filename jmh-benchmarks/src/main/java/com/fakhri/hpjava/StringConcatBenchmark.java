package com.fakhri.hpjava;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Thread)
public class StringConcatBenchmark {

    @Param({"10", "100", "1000"})
    int length;

    private String[] parts;

    @Setup
    public void setup() {
        parts = new String[length];
        for (int i = 0; i < length; i++) {
            parts[i] = "x";
        }
    }

    @Benchmark
    public void plusOperator(Blackhole bh) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s = s + parts[i];
        }
        bh.consume(s);
    }

    @Benchmark
    public void stringBuilder(Blackhole bh) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(parts[i]);
        }
        bh.consume(sb.toString());
    }

}
