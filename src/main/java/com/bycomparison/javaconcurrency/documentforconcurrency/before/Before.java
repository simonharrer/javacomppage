package com.bycomparison.javaconcurrency.documentforconcurrency.before;

public class Before {

}

// BEFORE:start
class Counter {

  private long count;

  synchronized void increment() {
    count++;
  }
}
// BEFORE:end