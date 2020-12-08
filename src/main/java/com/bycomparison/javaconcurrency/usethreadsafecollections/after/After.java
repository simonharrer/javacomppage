package com.bycomparison.javaconcurrency.usethreadsafecollections.after;

import java.util.ArrayList;
import java.util.Collections;
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

  void add(String name) {
    members.add(name);
  }
}
// AFTER:end