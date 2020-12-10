package com.bycomparison.javaconcurrency.avoidactivewaiting.before;

import java.util.concurrent.atomic.AtomicLong;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  private AtomicLong count = new AtomicLong();
  // END_HIGHLIGHT

  void increment() {
    // START_HIGHLIGHT
    count.incrementAndGet();
    // END_HIGHLIGHT
  }
}
// BEFORE:end