package com.bycomparison.javaconcurrency.usesynchronizedcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
/** thread-safe */
class Crew {

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
// BEFORE:end