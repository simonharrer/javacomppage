package com.bycomparison.javaconcurrency.minimizeyourlockscopes.after;

public class After {

}

@interface ThreadSafe {}

@interface GuardedBy {String value();}

// AFTER:start

/** thread safe */
class Counter {

  /** guarded by 'this' */
  private long count;

  void increment(long increment) {
    // START_HIGHLIGHT
    if (increment < 1) {
      return;
    }
    synchronized (this) {
      var next = count;
      next = next + increment;
      count = next;
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end