package com.bycomparison.javaconcurrency.avoidactivewaiting.before;

import java.util.concurrent.atomic.AtomicLong;

public class Before {

}

// BEFORE:start

/**
 * thread safe
 */
class Counter {

    // START_HIGHLIGHT
    private final AtomicLong count = new AtomicLong();
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only increments!");
        }

        // START_HIGHLIGHT
        this.count.addAndGet(amount);
        // END_HIGHLIGHT
    }
}
// BEFORE:end