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
    if (increment < 1) {
      throw new IllegalArgumentException();
    }

    // START_HIGHLIGHT
    synchronized (this) {
      var next = count;
      next = next + increment;
      count = next;
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end