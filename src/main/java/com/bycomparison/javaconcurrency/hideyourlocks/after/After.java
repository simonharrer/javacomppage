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

  void increment(long increment) {
    if (increment < 1) {
      return;
    }
    // START_HIGHLIGHT
    synchronized (lock) {
      // END_HIGHLIGHT
      var next = count;
      next = next + increment;
      count = next;
    }
  }
}
// AFTER:end