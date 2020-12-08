package com.bycomparison.javaconcurrency.synchronizecombinationofatomicmethods.after;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class After {

}

// AFTER:start
class Crew {

  private List<String> members;

  Crew() {
    members = new CopyOnWriteArrayList<>();
  }

  void remove(String name) {
    members.remove(name);
  }
}
// AFTER:end