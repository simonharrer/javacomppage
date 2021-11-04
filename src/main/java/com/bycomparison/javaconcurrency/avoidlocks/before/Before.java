package com.bycomparison.javaconcurrency.avoidlocks.before;

import java.util.concurrent.locks.ReentrantLock;

public class Before {

}

// BEFORE:start
class Counter {

    /**
     * guarded by 'lock'
     */
    private long count;

    // START_HIGHLIGHT
    private final ReentrantLock lock = new ReentrantLock(true);
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only increments!");
        }

        // START_HIGHLIGHT
        lock.lock();
        try {
            this.count += amount;
        } finally {
            this.lock.unlock();
        }
        // END_HIGHLIGHT
    }
}
// BEFORE:end