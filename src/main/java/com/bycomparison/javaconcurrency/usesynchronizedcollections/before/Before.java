package com.bycomparison.javaconcurrency.usesynchronizedcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
/** thread-safe */
class Crew {

  // START_HIGHLIGHT
  /** guarded by 'lock' */
  private final List<String> members = new ArrayList<>();
  // END_HIGHLIGHT

  // START_HIGHLIGHT
  private final Object lock = new Object();
  // END_HIGHLIGHT

  void add(String name) {
    // START_HIGHLIGHT
    synchronized (this.lock) {
      if (!this.members.contains(name)) {
        this.members.add(name);
      }
    }
    // END_HIGHLIGHT
  }

  int size() {
    // START_HIGHLIGHT
    synchronized (lock) {
      return this.members.size();
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end