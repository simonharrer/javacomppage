package com.bycomparison.javaconcurrency.avoidactivewaiting.before;

import java.util.concurrent.atomic.AtomicLong;

public class Before {

}

// BEFORE:start
/** thread safe */
class Counter {

  private AtomicLong count;

  void increment() {
    count.incrementAndGet();
  }
}
// BEFORE:end