package com.bycomparison.javaconcurrency.synchronizecombinationofatomicmethods.before;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Before {

}

// BEFORE:start
class Crew {

  private List<String> members = new CopyOnWriteArrayList<>();

  void remove(String name) {
    // START_HIGHLIGHT
    var index = members.indexOf(name);
    members.remove(index);
    // END_HIGHLIGHT
  }
}
// BEFORE:end