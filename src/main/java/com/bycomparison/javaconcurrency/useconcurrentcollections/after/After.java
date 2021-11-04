package com.bycomparison.javaconcurrency.useconcurrentcollections.after;

import java.util.concurrent.CopyOnWriteArrayList;

public class After {}

// AFTER:start
/** thread-safe */
class Crew {

  // START_HIGHLIGHT
  private final CopyOnWriteArrayList<String> members = new CopyOnWriteArrayList<>();
  // END_HIGHLIGHT

  void add(String name) {
    // START_HIGHLIGHT
    this.members.addIfAbsent(name);
    // END_HIGHLIGHT
  }

  int size() {
    return this.members.size();
  }
}
// AFTER:end
