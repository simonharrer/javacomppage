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
  /** guarded by 'members' */
  // END_HIGHLIGHT
  private final List<String> members = new ArrayList<>();

  void add(String name) {
    // START_HIGHLIGHT
    synchronized (this.members) {
      if (!this.members.contains(name)) {
        this.members.add(name);
      }
    }
    // END_HIGHLIGHT
  }

  int size() {
    // START_HIGHLIGHT
    synchronized (this.members) {
      return this.members.size();
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end
