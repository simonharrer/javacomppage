package com.bycomparison.javaconcurrency.protectyourcollections.after;

import java.util.ArrayList;
import java.util.List;

public class After {

}

// AFTER:start
class Crew {

  private List<String> members;

  private final Object lock = new Object();

  Crew() {
    members = new ArrayList<>();
  }

  void add(String name) {
    synchronized (lock) {
      members.add(name);
    }
  }
}
// AFTER:end