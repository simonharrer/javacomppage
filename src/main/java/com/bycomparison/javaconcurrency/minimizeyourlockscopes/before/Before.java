package com.bycomparison.javaconcurrency.minimizeyourlockscopes.before;

public class Before {

}

// BEFORE:start

/** thread safe */
class Counter {

  /** guarded by 'this' */
  private long count;

  void increment(long increment) {
    // START_HIGHLIGHT
    synchronized (this) {
      if (increment < 1) {
        return;
      }
      var next = count;
      next = next + increment;
      count = next;
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end