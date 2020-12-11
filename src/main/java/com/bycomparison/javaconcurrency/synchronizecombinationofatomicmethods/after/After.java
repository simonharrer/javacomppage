package com.bycomparison.javaconcurrency.synchronizecombinationofatomicmethods.after;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class After {

}

// AFTER:start
class Crew {

  private List<String> members = new CopyOnWriteArrayList<>();

  void remove(String name) {
    // START_HIGHLIGHT
    members.remove(name);
    // END_HIGHLIGHT
  }
}
// AFTER:end