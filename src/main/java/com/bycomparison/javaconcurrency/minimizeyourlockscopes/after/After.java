package com.bycomparison.javaconcurrency.minimizeyourlockscopes.after;

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
        if (amount < 1) {
            throw new IllegalArgumentException("Only positive increments!");
        }

        // START_HIGHLIGHT
        synchronized (this) {
            this.count += amount;
        }
        // END_HIGHLIGHT
    }
}
// AFTER:end