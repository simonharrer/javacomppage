package com.bycomparison.javaconcurrency.makelocksexplicit.after;

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
    synchronized (this) {
    // END_HIGHLIGHT
      if (increment < 1) {
        throw new IllegalArgumentException();
      }

      var next = count;
      next = next + increment;
      count = next;
    // START_HIGHLIGHT
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end