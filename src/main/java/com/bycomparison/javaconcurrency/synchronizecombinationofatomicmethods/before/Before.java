package com.bycomparison.javaconcurrency.synchronizecombinationofatomicmethods.before;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Before {

}

// BEFORE:start
class Crew {

  private List<String> members = new CopyOnWriteArrayList<>();

  void add(String name) {
    // START_HIGHLIGHT
    if(!members.contains(name)) {
      members.add(name);
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end