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

  void increment() {
    // START_HIGHLIGHT
    synchronized (this) {
      count++;
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end