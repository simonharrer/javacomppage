package com.bycomparison.javaconcurrency.makelocksexplicit.before;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  /** guarded by 'this' */
  private long count;

  // START_HIGHLIGHT
  synchronized void increment(long increment) {
    // END_HIGHLIGHT
    if (increment < 1) {
      return;
    }
    var next = count;
    next = next + increment;
    count = next;
  }
}
// BEFORE:end