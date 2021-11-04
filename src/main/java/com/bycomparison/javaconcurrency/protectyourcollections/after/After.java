package com.bycomparison.javaconcurrency.protectyourcollections.after;

import java.util.ArrayList;
import java.util.List;

public class After {}

// AFTER:start
// START_HIGHLIGHT
/** thread-safe */
// END_HIGHLIGHT
class Crew {

  // START_HIGHLIGHT
  /** guarded by 'lock' */
  // END_HIGHLIGHT
  private final List<String> members = new ArrayList<>();

  // START_HIGHLIGHT
  private final Object lock = new Object();
  // END_HIGHLIGHT

  void add(String name) {
    // START_HIGHLIGHT
    synchronized (lock) {
      if (!members.contains(name)) {
        members.add(name);
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
// AFTER:end
