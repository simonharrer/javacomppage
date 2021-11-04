package com.bycomparison.javaconcurrency.usefairlocks.after;

import java.util.concurrent.locks.ReentrantLock;

public class After {

}

@interface ThreadSafe {

}

@interface GuardedBy {

    String value();
}

// AFTER:start

/**
 * thread safe
 */
class Counter {

    /**
     * guarded by 'lock'
     */
    private long count;

    // START_HIGHLIGHT
    private final ReentrantLock lock =
            new ReentrantLock(true);
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only increments!");
        }

        // START_HIGHLIGHT
        this.lock.lock();
        try {
            this.count += amount;
        } finally {
            this.lock.unlock();
        }
        // END_HIGHLIGHT
    }
}
// AFTER:end