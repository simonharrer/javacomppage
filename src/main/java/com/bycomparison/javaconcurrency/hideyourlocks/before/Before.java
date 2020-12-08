package com.bycomparison.javaconcurrency.hideyourlocks.before;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  /** guarded by 'this' */
  private long count;

  synchronized void increment() {
    count++;
  }
}
// BEFORE:end