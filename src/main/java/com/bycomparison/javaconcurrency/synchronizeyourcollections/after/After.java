package com.bycomparison.javaconcurrency.synchronizeyourcollections.after;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class After {

}

// AFTER:start
class Crew {

  private List<String> members;

  Crew() {
    // START_HIGHLIGHT
    members = Collections.synchronizedList(new ArrayList<>());
    // END_HIGHLIGHT
  }

  void add(String name) {
    members.add(name);
  }
}
// AFTER:end