package com.bycomparison.javaconcurrency.protectyoursharedmemory.before;

public class Before {

}

// BEFORE:start
class Counter {

  private long count;

  // START_HIGHLIGHT
  void increment(long increment) {
    if (increment < 1) {
      return;
    }
    var next = count;
    next = next + increment;
    count = next;
  }
  // END_HIGHLIGHT
}
// BEFORE:end