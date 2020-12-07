package com.bycomparison.javaconcurrency.documentforconcurrency.after;

public class After {

}
@interface ThreadSafe {}
@interface GuardedBy {String value();}

// AFTER:start
/** thread safe */
class Counter {

  /** guarded by 'this' */
  private int count;

  synchronized void increment() {
    count++;
  }
}
// AFTER:end