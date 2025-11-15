package com.fakhri.lockfree;

public class QueueThroughputDemo {

    private static final int QUEUE_CAPACITY = 1024;          // power of two
    private static final long OPERATIONS_PER_RUN = 10_000_000L;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("== Locked queue ==");
        runBenchmark(new LockedIntQueue(QUEUE_CAPACITY));

        System.out.println();
        System.out.println("== Lock-free ring buffer ==");
        runBenchmark(new LockFreeRingBuffer(QUEUE_CAPACITY));
    }

    private static void runBenchmark(IntQueue queue) throws InterruptedException {
        long ops = OPERATIONS_PER_RUN;

        // Producer
        Thread producer = new Thread(() -> {
            long produced = 0;
            int value = 1;
            while (produced < ops) {
                if (queue.offer(value)) {
                    produced++;
                    value++;
                }
            }
        }, "producer");

        // Consumer
        Thread consumer = new Thread(() -> {
            long consumed = 0;
            while (consumed < ops) {
                int v = queue.poll();
                if (v != Integer.MIN_VALUE) {
                    consumed++;
                }
            }
        }, "consumer");

        long start = System.nanoTime();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;
        double throughput = ops / seconds;

        System.out.printf("Time: %.3f s, ops: %d, throughput: %.2f ops/s%n",
                seconds, ops, throughput);
    }
}
