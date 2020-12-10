package com.bycomparison.javaconcurrency.avoidactivewaiting.after;

import java.util.concurrent.atomic.LongAdder;

public class After {

}

// AFTER:start
/** thread safe */
class Counter {

  // START_HIGHLIGHT
  private LongAdder count = new LongAdder();
  // END_HIGHLIGHT

  void increment() {
    // START_HIGHLIGHT
    count.increment();
    // END_HIGHLIGHT
  }
}
// AFTER:end