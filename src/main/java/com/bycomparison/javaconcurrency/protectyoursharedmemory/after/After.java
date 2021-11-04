package com.bycomparison.javaconcurrency.protectyoursharedmemory.after;

public class After {

}

// AFTER:start
class Counter {

    private long count;

    // START_HIGHLIGHT
    synchronized void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only positive increments!");
        }

        this.count += amount;
    }
    // END_HIGHLIGHT
}
// AFTER:end