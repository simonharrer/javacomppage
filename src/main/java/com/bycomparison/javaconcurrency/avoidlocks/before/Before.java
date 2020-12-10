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

  void increment() {
    // START_HIGHLIGHT
    synchronized (lock) {
      count++;
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end