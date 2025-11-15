package com.fakhri.lockfree;

public interface IntQueue {

    boolean offer(int value);

    int poll(); // returns Integer.MIN_VALUE when empty
}
