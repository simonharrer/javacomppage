package com.bycomparison.javaconcurrency.makelocksexplicit.before;

public class Before {

}

// BEFORE:start

/**
 * thread safe
 */
class Counter {

    /**
     * guarded by 'this'
     */
    private long count;

    // START_HIGHLIGHT
    synchronized void increment(long amount) {
        // END_HIGHLIGHT
        if (amount < 1) {
            throw new IllegalArgumentException("Only increments!");
        }

        this.count += amount;
    }
}
// BEFORE:end