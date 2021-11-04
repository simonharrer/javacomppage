package com.bycomparison.javaconcurrency.makelocksexplicit.after;

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
     * guarded by 'this'
     */
    private long count;

    void increment(long amount) {
        // START_HIGHLIGHT
        synchronized (this) {
            // END_HIGHLIGHT
            if (amount < 1) {
                throw new IllegalArgumentException("Only increments!");
            }

            this.count += amount;
            // START_HIGHLIGHT
        }
        // END_HIGHLIGHT
    }
}
// AFTER:end