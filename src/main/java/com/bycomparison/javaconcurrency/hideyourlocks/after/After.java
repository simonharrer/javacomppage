package com.bycomparison.javaconcurrency.hideyourlocks.after;

public class After {

}

@interface ThreadSafe {}

@interface GuardedBy {String value();}

// AFTER:start

/** thread safe */
class Counter {

  /** guarded by 'lock' */
  private int count;

  private final Object lock = new Object();

  void increment() {
    synchronized (lock) {
      count++;
    }
  }
}
// AFTER:end