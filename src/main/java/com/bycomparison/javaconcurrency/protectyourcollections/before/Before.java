package com.bycomparison.javaconcurrency.protectyourcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
// START_HIGHLIGHT
/** not thread-safe */
// END_HIGHLIGHT
class Crew {

  private final List<String> members = new ArrayList<>();

  void add(String name) {
    // START_HIGHLIGHT
    if (!members.contains(name)) {
      members.add(name);
    }
    // END_HIGHLIGHT
  }

  int size(){
    // START_HIGHLIGHT
    return this.members.size();
    // END_HIGHLIGHT
  }
}
// BEFORE:end