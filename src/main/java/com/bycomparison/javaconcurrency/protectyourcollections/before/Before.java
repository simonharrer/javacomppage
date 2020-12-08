package com.bycomparison.javaconcurrency.protectyourcollections.before;

import java.util.ArrayList;
import java.util.List;

public class Before {

}

// BEFORE:start
class Crew {

  private List<String> members;

  Crew() {
    members = new ArrayList<>();
  }

  void add(String name) {
    members.add(name);
  }
}
// BEFORE:end