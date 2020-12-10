package com.bycomparison.javaconcurrency.protectyoursharedmemory.before;

public class Before {

}

// BEFORE:start
class Counter {

  private long count;

  // START_HIGHLIGHT
  void increment() {
    count++;
  }
  // END_HIGHLIGHT
}
// BEFORE:end