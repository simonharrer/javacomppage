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

  synchronized void increment() {
    count++;
  }
}
// AFTER:end