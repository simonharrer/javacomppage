package com.bycomparison.javaconcurrency.avoidactivewaiting.after;

import java.util.concurrent.atomic.LongAdder;

public class After {

}

// AFTER:start

/**
 * thread safe
 */
class Counter {

    // START_HIGHLIGHT
    private final LongAdder count = new LongAdder();
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only positive increments!");
        }

        // START_HIGHLIGHT
        this.count.add(amount);
        // END_HIGHLIGHT
    }
}
// AFTER:end