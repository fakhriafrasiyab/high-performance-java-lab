# StringBuilder Capacity Benchmark (JMH)

This module contains a JMH benchmark that compares two approaches of building strings in Java:

- **StringBuilder without initial capacity**
- **StringBuilder with predefined capacity**

The goal is to demonstrate how internal buffer resizing affects performance and why pre-allocating capacity leads to more predictable and faster results for larger string-building tasks.

---

## 🚀 Benchmark Description

### 1. `builderWithoutCapacity`

Creates a `StringBuilder` with default capacity and appends `"x"` repeatedly.

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < length; i++) {
    sb.append("x");
}
```

###2. 'builderWithCapacity`

Creates a StringBuilder with an initial capacity equal to the final string size.

```java
StringBuilder sb = new StringBuilder(length);
for (int i = 0; i < length; i++) {
    sb.append("x");
}
```

jmh-benchmarks/
│
├── src/main/java/com/fakhri/hpjava/
│   ├── StringBuilderCapacityBenchmark.java
│   └── BenchmarkRunner.java
│
├── pom.xml
└── README.md


