package com.bycomparison.javaconcurrency.synchronizecombinationofatomicmethods.before;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Before {

}

// BEFORE:start
class Crew {

  private List<String> members;

  Crew() {
    members = new CopyOnWriteArrayList<>();
  }

  void remove(String name) {
    var index = members.indexOf(name);
    members.remove(index);
  }
}
// BEFORE:end