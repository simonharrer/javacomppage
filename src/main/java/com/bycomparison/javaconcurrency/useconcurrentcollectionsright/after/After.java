package com.bycomparison.javaconcurrency.useconcurrentcollectionsright.after;

import java.util.concurrent.CopyOnWriteArrayList;

public class After {

}

// AFTER:start
class Crew {

  // START_HIGHLIGHT
  private CopyOnWriteArrayList<String> members = new CopyOnWriteArrayList<>();
  // END_HIGHLIGHT

  void remove(String name) {
    // START_HIGHLIGHT
    members.addIfAbsent(name);
    // END_HIGHLIGHT
  }
}
// AFTER:end