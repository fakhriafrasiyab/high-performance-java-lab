package com.fakhri.lockfree;

import java.util.concurrent.atomic.AtomicLong;

public class LockFreeRingBuffer implements IntQueue {

    private final int[] buffer;
    private final int mask;

    // head = next position to read
    private final AtomicLong head = new AtomicLong(0);
    // tail = next position to write
    private final AtomicLong tail = new AtomicLong(0);

    public LockFreeRingBuffer(int capacityPowerOfTwo) {
        if (Integer.bitCount(capacityPowerOfTwo) != 1) {
            throw new IllegalArgumentException("Capacity must be a power of two");
        }
        this.buffer = new int[capacityPowerOfTwo];
        this.mask = capacityPowerOfTwo - 1;
    }

    @Override
    public boolean offer(int value) {
        long currentTail = tail.get();
        long wrapPoint = currentTail - buffer.length;

        // if head <= wrapPoint then buffer is full
        if (head.get() <= wrapPoint) {
            return false;
        }

        int index = (int) (currentTail & mask);
        buffer[index] = value;
        // lazySet = cheaper store, enough for SPSC ordering
        tail.lazySet(currentTail + 1);
        return true;
    }

    @Override
    public int poll() {
        long currentHead = head.get();
        if (currentHead >= tail.get()) {
            return Integer.MIN_VALUE; // empty
        }

        int index = (int) (currentHead & mask);
        int value = buffer[index];
        head.lazySet(currentHead + 1);
        return value;
    }

}
