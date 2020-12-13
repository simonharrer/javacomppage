package com.bycomparison.javaconcurrency.protectyourcollections.after;

import java.util.ArrayList;
import java.util.List;

public class After {

}

// AFTER:start
// START_HIGHLIGHT
/** thread-safe */
// END_HIGHLIGHT
class Crew {

  // START_HIGHLIGHT
  /** guarded by 'lock' */
  // END_HIGHLIGHT
  private List<String> members = new ArrayList<>();

  // START_HIGHLIGHT
  private final Object lock = new Object();
  // END_HIGHLIGHT

  void add(String name) {
    // START_HIGHLIGHT
    synchronized (lock) {
    // END_HIGHLIGHT
      members.add(name);
    // START_HIGHLIGHT
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end