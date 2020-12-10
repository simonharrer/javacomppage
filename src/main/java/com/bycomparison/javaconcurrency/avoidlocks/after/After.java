package com.bycomparison.javaconcurrency.avoidlocks.after;

import java.util.concurrent.atomic.AtomicLong;

public class After {

}

// AFTER:start
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
// AFTER:end