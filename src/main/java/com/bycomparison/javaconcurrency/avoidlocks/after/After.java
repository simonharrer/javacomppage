package com.bycomparison.javaconcurrency.avoidlocks.after;

import java.util.concurrent.atomic.AtomicLong;

public class After {

}

// AFTER:start

/**
 * thread safe
 */
class Counter {

    // START_HIGHLIGHT
    private final AtomicLong count = new AtomicLong();
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only positive increments!");
        }

        // START_HIGHLIGHT
        this.count.addAndGet(amount);
        // END_HIGHLIGHT
    }
}
// AFTER:end