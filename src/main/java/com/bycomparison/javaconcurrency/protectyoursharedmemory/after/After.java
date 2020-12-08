package com.bycomparison.javaconcurrency.protectyoursharedmemory.after;

public class After {

}

// AFTER:start
class Counter {

  private long count;

  synchronized void increment() {
    count++;
  }
}
// AFTER:end