package com.bycomparison.javaconcurrency.documentforconcurrency.before;

public class Before {

}

// BEFORE:start
// START_HIGHLIGHT
class Counter {
  // END_HIGHLIGHT

  // START_HIGHLIGHT
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
// BEFORE:end