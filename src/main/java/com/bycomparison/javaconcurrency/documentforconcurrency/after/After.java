package com.bycomparison.javaconcurrency.documentforconcurrency.after;

public class After {

}
@interface ThreadSafe {}
@interface GuardedBy {String value();}

// AFTER:start
// START_HIGHLIGHT
/** thread safe */
class Counter {
  // END_HIGHLIGHT

  // START_HIGHLIGHT
  /** guarded by 'this' */
  private long count;
  // END_HIGHLIGHT

  synchronized void increment(long increment) {
    if (increment < 1) {
      throw new IllegalArgumentException();
    }

    var next = count;
    next = next + increment;
    count = next;
  }
}
// AFTER:end