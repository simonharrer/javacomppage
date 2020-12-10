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

  synchronized void increment() {
    count++;
  }
}
// BEFORE:end