package com.bycomparison.javaconcurrency.protectyourcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
/** not thread-safe */
class Crew {

  private List<String> members;

  Crew() {
    members = new ArrayList<>();
  }

  void add(String name) {
    // START_HIGHLIGHT
    members.add(name);
    // END_HIGHLIGHT
  }
}
// BEFORE:end