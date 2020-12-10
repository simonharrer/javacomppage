package com.bycomparison.javaconcurrency.protectyourcollections.after;

import java.util.ArrayList;
import java.util.List;

public class After {

}

// AFTER:start
class Crew {

  private List<String> members;

  // START_HIGHLIGHT
  private final Object lock = new Object();
  // END_HIGHLIGHT

  Crew() {
    members = new ArrayList<>();
  }

  void add(String name) {
    // START_HIGHLIGHT
    synchronized (lock) {
      members.add(name);
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end