package com.bycomparison.javaconcurrency.avoidlocks.before;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  /** guarded by 'lock' */
  private long count;

  private final Object lock = new Object();
  // END_HIGHLIGHT

  void increment(long increment) {
    if (increment < 1) {
      return;
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