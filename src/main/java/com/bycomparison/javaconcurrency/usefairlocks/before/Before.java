package com.bycomparison.javaconcurrency.usefairlocks.before;

public class Before {

}

// BEFORE:start

/**
 * thread safe
 */
class Counter {

    /**
     * guarded by 'lock'
     */
    private long count;

    // START_HIGHLIGHT
    private final Object lock = new Object();
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only increments!");
        }

        // START_HIGHLIGHT
        synchronized (this.lock) {
            this.count += amount;
        }
        // END_HIGHLIGHT
    }
}
// BEFORE:end