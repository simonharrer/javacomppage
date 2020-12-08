package com.bycomparison.javaconcurrency.avoidactivewaiting.after;

import java.util.concurrent.atomic.LongAdder;

public class After {

}

// AFTER:start
/** thread safe */
class Counter {

  private LongAdder count;

  void increment() {
    count.increment();
  }
}
// AFTER:end