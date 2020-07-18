package com.bycomparison.c2.before;

public class Before {

}

// BEFORE:start
class ChocolateBars {

  private int amount;

  synchronized void eat() {
    if (amount < 1) {
      throw new RuntimeException("NOOOO!");
    }
    amount--;
  }
}
// BEFORE:end