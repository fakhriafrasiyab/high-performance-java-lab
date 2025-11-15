StringBuilder Capacity Benchmark (JMH)

This module contains a JMH benchmark that compares two common approaches of building strings in Java:

StringBuilder without initial capacity

StringBuilder with predefined capacity

The goal is to show how buffer resizing affects performance and why pre-sizing can give faster and more predictable results, especially for larger string-building tasks.

Benchmark Description
1. builderWithoutCapacity

Creates a StringBuilder with default capacity and appends "x" repeatedly.

StringBuilder sb = new StringBuilder();
for (int i = 0; i < length; i++) {
    sb.append("x");
}

2. builderWithCapacity

Creates a StringBuilder with exact required capacity to avoid resizing.

StringBuilder sb = new StringBuilder(length);
for (int i = 0; i < length; i++) {
    sb.append("x");
}

Parameters

The benchmark runs with different string sizes:

10
100
1000


This shows how performance changes as the amount of appended data grows.

Why Capacity Matters

StringBuilder grows its internal buffer when needed.
This resizing causes:

new array allocations

copying old data into new array

garbage creation

extra CPU + memory work

When the final size is known, providing initial capacity:

avoids all resizing

reduces allocations

lowers GC pressure

improves throughput

Expected Behavior

Small lengths (10):

Performance is similar.

Medium (100):

Pre-allocated version is faster.

Large (1000):

Pre-allocated version can be 2×–3× faster because dynamic version performs multiple buffer expansions.

Benchmark Code
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

How to Run the Benchmark

Use the provided BenchmarkRunner:

public static void main(String[] args) throws Exception {
    Options opt = new OptionsBuilder()
            .include(StringBuilderCapacityBenchmark.class.getSimpleName())
            .build();

    new Runner(opt).run();
}

Run in IntelliJ

Open BenchmarkRunner.java

Click Run ▶

Run via command-line

Build first:

mvn clean compile


Then run:

java -cp target/classes:<your_jmh_dependencies_classpath> com.fakhri.hpjava.BenchmarkRunner

Project Structure
jmh-benchmarks/
│
├── src/
│   └── main/java/com/fakhri/hpjava/
│        ├── StringBuilderCapacityBenchmark.java
│        └── BenchmarkRunner.java
│
└── pom.xml
└── README.md
