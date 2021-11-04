package com.bycomparison.javaconcurrency.hideyourlocks.after;

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

    // START_HIGHLIGHT
    /**
     * guarded by 'lock'
     */
    // END_HIGHLIGHT
    private long count;

    // START_HIGHLIGHT
    private final Object lock = new Object();
    // END_HIGHLIGHT

    void increment(long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Only positive increments!");
        }

        // START_HIGHLIGHT
        synchronized (this.lock) {
            // END_HIGHLIGHT
            this.count += amount;
        }
    }
}
// AFTER:end