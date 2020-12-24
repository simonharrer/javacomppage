package com.bycomparison.javaconcurrency.hideyourlocks.before;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  /** guarded by 'this' */
  // END_HIGHLIGHT
  private long count;

  void increment(long increment) {
    if (increment < 1) {
      throw new IllegalArgumentException();
    }

    // START_HIGHLIGHT
    synchronized (this) {
      // END_HIGHLIGHT
      var next = count;
      next = next + increment;
      count = next;
    }
  }
}
// BEFORE:end