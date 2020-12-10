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

  // START_HIGHLIGHT
  synchronized void increment() {
    // END_HIGHLIGHT
    count++;
  }
}
// BEFORE:end