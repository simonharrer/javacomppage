package com.bycomparison.javaconcurrency.makelocksexplicit.after;

public class After {

}

@interface ThreadSafe {}

@interface GuardedBy {String value();}

// AFTER:start
/** thread safe */
class Counter {

  /** guarded by 'this' */
  private int count;

  void increment() {
    // START_HIGHLIGHT
    synchronized (this) {
    // END_HIGHLIGHT
      count++;
    // START_HIGHLIGHT
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end