package com.bycomparison.javaconcurrency.usesynchronizedcollections.after;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class After {}

// AFTER:start
/** thread-safe */
class Crew {

  // START_HIGHLIGHT
  /** guarded by 'members' */
  private final List<String> members = Collections.synchronizedList(new ArrayList<>());
  // END_HIGHLIGHT

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
    return this.members.size();
    // END_HIGHLIGHT
  }
}
// AFTER:end
