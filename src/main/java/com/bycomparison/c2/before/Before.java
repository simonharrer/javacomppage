package com.bycomparison.c2.before;

public class Before {

}

// BEFORE:start
class Counter {

  private int count;

  synchronized void increment() {
    count++;
  }
}
// BEFORE:end