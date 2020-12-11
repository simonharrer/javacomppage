package com.bycomparison.javaconcurrency.usethreadsafecollections.after;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class After {

}

// AFTER:start
/** thread-safe */
class Crew {

  // START_HIGHLIGHT
  private List<String> members = new CopyOnWriteArrayList<>();
  // END_HIGHLIGHT

  void add(String name) {
    members.add(name);
  }
}
// AFTER:end