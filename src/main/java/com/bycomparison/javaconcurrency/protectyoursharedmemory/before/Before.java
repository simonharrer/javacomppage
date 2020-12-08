package com.bycomparison.javaconcurrency.protectyoursharedmemory.before;

public class Before {

}

// BEFORE:start
class Counter {

  private long count;

  void increment() {
    count++;
  }
}
// BEFORE:end