package com.bycomparison.javaconcurrency.usesynchronizedcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {}

// BEFORE:start
/** thread-safe */
class Crew {

  /** guarded by 'members' */
  // START_HIGHLIGHT
  private final List<String> members = new ArrayList<>();
  // END_HIGHLIGHT

  void add(String name) {
    synchronized (this.members) {
      if (!this.members.contains(name)) {
        this.members.add(name);
      }
    }
  }

  int size() {
    // START_HIGHLIGHT
    synchronized (this.members) {
      return this.members.size();
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end
