package com.bycomparison.javaconcurrency.hideyourlocks.before;

public class Before {}

// BEFORE:start

/** thread safe */
class Counter {

  // START_HIGHLIGHT
  /** guarded by 'this' */
  // END_HIGHLIGHT
  private long count;

  void increment(long amount) {
    if (amount < 1) {
      throw new IllegalArgumentException("Only increments!");
    }

    // START_HIGHLIGHT
    synchronized (this) {
      // END_HIGHLIGHT
      this.count += amount;
    }
  }
}
// BEFORE:end
