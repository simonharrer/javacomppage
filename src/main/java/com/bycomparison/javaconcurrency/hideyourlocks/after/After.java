package com.bycomparison.javaconcurrency.hideyourlocks.after;

public class After {

}

@interface ThreadSafe {}

@interface GuardedBy {String value();}

// AFTER:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  /** guarded by 'lock' */
  // END_HIGHLIGHT
  private long count;

  // START_HIGHLIGHT
  private final Object lock = new Object();
  // END_HIGHLIGHT

  void increment() {
    // START_HIGHLIGHT
    synchronized (lock) {
      // END_HIGHLIGHT
      count++;
      // START_HIGHLIGHT
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end