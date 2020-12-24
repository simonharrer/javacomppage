package com.bycomparison.javaconcurrency.protectyoursharedmemory.after;

public class After {

}

// AFTER:start
class Counter {

  private long count;

  // START_HIGHLIGHT
  synchronized void increment(long increment) {
    if (increment < 1) {
      throw new IllegalArgumentException();
    }

    var next = count;
    next = next + increment;
    count = next;
  }
  // END_HIGHLIGHT
}
// AFTER:end