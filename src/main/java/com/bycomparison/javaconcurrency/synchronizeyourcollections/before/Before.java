package com.bycomparison.javaconcurrency.synchronizeyourcollections.before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Before {

}

// BEFORE:start
class Crew {

  private List<String> members;

  private final Object lock = new Object();

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
// BEFORE:end