package com.bycomparison.javaconcurrency.befair.before;

public class Before {

}

// BEFORE:start

/** thread safe */
class Counter {

  /** guarded by 'lock' */
  private long count;

  // START_HIGHLIGHT
  private final Object lock = new Object();
  // END_HIGHLIGHT

  void increment(long increment) {
    if (increment < 1) {
      throw new IllegalArgumentException();
    }

    // START_HIGHLIGHT
    synchronized (lock) {
      var next = count;
      next = next + increment;
      count = next;
    }
      // END_HIGHLIGHT
  }
}
// BEFORE:end