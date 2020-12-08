package com.bycomparison.javaconcurrency.avoidlocks.after;

import java.util.concurrent.atomic.AtomicLong;

public class After {

}

// AFTER:start
/** thread safe */
class Counter {

  private AtomicLong count;

  void increment() {
    count.incrementAndGet();
  }
}
// AFTER:end