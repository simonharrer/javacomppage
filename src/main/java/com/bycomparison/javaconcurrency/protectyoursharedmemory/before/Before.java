package com.bycomparison.javaconcurrency.protectyoursharedmemory.before;

public class Before {

}

// BEFORE:start
class Counter {

    private long count;

    // START_HIGHLIGHT
    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only positive increments!");
        }

        this.count += amount;
    }
    // END_HIGHLIGHT
}
// BEFORE:end