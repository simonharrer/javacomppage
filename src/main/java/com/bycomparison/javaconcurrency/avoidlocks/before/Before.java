package com.bycomparison.javaconcurrency.avoidlocks.before;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  /** guarded by 'lock' */
  private long count;

  private final Object lock = new Object();

  void increment() {
    synchronized (lock) {
      count++;
    }
  }
}
// BEFORE:end