package com.bycomparison.javaconcurrency.avoidlocks.after;

import java.util.concurrent.atomic.AtomicLong;

public class After {

}

// AFTER:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  private final AtomicLong count = new AtomicLong();
  // END_HIGHLIGHT

  void increment(long increment) {
    if (increment < 1) {
      return;
    }
    // START_HIGHLIGHT
    count.addAndGet(increment);
    // END_HIGHLIGHT
  }
}
// AFTER:end