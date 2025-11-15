package com.fakhri.jmm;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(ImprovedCounterBenchmark.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(5)
                .forks(2)
                .build();

        new Runner(opt).run();
    }

}
