package com.bycomparison.javaconcurrency.useconcurrentcollections.after;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class After {

}

// AFTER:start
/** thread-safe */
class Crew {

  // START_HIGHLIGHT
  private final CopyOnWriteArrayList<String> members = new CopyOnWriteArrayList<>();
  // END_HIGHLIGHT

  void add(String name) {
    // START_HIGHLIGHT
    members.addIfAbsent(name);
    // END_HIGHLIGHT
  }
}
// AFTER:end