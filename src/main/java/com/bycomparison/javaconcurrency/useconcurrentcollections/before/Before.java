package com.bycomparison.javaconcurrency.useconcurrentcollections.before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Before {}

// BEFORE:start
/** thread-safe */
class Crew {

  // START_HIGHLIGHT
  private final List<String> members = Collections.synchronizedList(new ArrayList<>());
  // END_HIGHLIGHT

  void add(String name) {
    // START_HIGHLIGHT
    synchronized (this.members) {
      if (!members.contains(name)) {
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
// BEFORE:end
