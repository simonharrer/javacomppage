package com.bycomparison.javaconcurrency.makelocksexplicit.before;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  /** guarded by 'this' */
  private int count;

  // START_HIGHLIGHT
  synchronized void increment() {
    // END_HIGHLIGHT
    count++;
  }
}
// BEFORE:end