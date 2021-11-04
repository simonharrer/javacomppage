package com.bycomparison.javaconcurrency.documentforconcurrency.before;

public class Before {

}

// BEFORE:start
// START_HIGHLIGHT
class Counter {
    // END_HIGHLIGHT

    // START_HIGHLIGHT
    private long count;
    // END_HIGHLIGHT

    synchronized void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only increments!");
        }

        this.count += amount;
    }
}
// BEFORE:end