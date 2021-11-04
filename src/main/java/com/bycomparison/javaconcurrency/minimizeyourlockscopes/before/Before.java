package com.bycomparison.javaconcurrency.minimizeyourlockscopes.before;

public class Before {}

// BEFORE:start

/** thread safe */
class Counter {

  /** guarded by 'this' */
  private long count;

  void increment(long amount) {
    // START_HIGHLIGHT
    synchronized (this) {
      if (amount < 1) {
        throw new IllegalArgumentException("Only increments!");
      }

      this.count += amount;
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end
