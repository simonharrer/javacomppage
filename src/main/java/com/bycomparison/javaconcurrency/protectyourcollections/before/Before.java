package com.bycomparison.javaconcurrency.protectyourcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
/** not thread-safe */
class Crew {

  private List<String> members = new ArrayList<>();

  void add(String name) {
    members.add(name);
  }
}
// BEFORE:end