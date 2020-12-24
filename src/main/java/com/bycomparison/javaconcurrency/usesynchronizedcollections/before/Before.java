package com.bycomparison.javaconcurrency.usesynchronizedcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
/** thread-safe */
class Crew {

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
}
// BEFORE:end