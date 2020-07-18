package com.bycomparison.c2.after;

public class After {

}
@interface ThreadSafe {}
@interface GuardedBy {String value();}

// AFTER:start
@ThreadSafe
class ChocolateBars {

  @GuardedBy("this")
  private int amount;

  synchronized void eat() {
    if (amount < 1) {
      throw new RuntimeException("NOOOO!");
    }
    amount--;
  }
}
// AFTER:end