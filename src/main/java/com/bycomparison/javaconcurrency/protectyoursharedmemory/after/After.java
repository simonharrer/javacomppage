package com.bycomparison.javaconcurrency.protectyoursharedmemory.after;

public class After {

}

// AFTER:start
class Counter {

  private long count;

  // START_HIGHLIGHT
  synchronized void increment() {
    count++;
  }
  // END_HIGHLIGHT
}
// AFTER:end