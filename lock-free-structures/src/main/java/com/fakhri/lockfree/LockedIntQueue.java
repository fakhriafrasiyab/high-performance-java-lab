package com.fakhri.lockfree;

import java.util.concurrent.locks.ReentrantLock;

public class LockedIntQueue implements IntQueue {

    private final int[] buffer;
    private final int mask;
    private int head = 0;
    private int tail = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public LockedIntQueue(int capacityPowerOfTwo) {
        if (Integer.bitCount(capacityPowerOfTwo) != 1) {
            throw new IllegalArgumentException("Capacity must be a power of two");
        }
        this.buffer = new int[capacityPowerOfTwo];
        this.mask = capacityPowerOfTwo - 1;
    }

    @Override
    public boolean offer(int value) {
        lock.lock();
        try {
            int nextTail = (tail + 1) & mask;
            if (nextTail == head) {
                // full
                return false;
            }
            buffer[tail] = value;
            tail = nextTail;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int poll() {
        lock.lock();
        try {
            if (head == tail) {
                return Integer.MIN_VALUE; // empty
            }
            int value = buffer[head];
            head = (head + 1) & mask;
            return value;
        } finally {
            lock.unlock();
        }
    }



}
