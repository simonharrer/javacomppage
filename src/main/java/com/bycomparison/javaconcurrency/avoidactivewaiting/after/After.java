package com.bycomparison.javaconcurrency.avoidactivewaiting.after;

import java.util.concurrent.atomic.LongAdder;

public class After {

}

// AFTER:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  private final LongAdder count = new LongAdder();
  // END_HIGHLIGHT

  void increment(long increment) {
    if (increment < 1) {
      return;
    }
    // START_HIGHLIGHT
    count.add(increment);
    // END_HIGHLIGHT
  }
}
// AFTER:end