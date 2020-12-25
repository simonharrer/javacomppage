package com.bycomparison.javaconcurrency.avoidlocks.before;

import java.util.concurrent.locks.ReentrantLock;

public class Before {

}

// BEFORE:start
class Counter {

  /** guarded by 'lock' */
  private long count;

  // START_HIGHLIGHT
  private final ReentrantLock lock = new ReentrantLock(true);
  // END_HIGHLIGHT

  void increment(long increment) {
    if (increment < 1) {
      throw new IllegalArgumentException();
    }

    // START_HIGHLIGHT
    lock.lock();
    try {
      var next = count;
      next = next + increment;
      count = next;
    } finally {
      lock.unlock();
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end