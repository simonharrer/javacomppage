package com.bycomparison.javaconcurrency.usesynchronizedcollections.after;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class After {

}

// AFTER:start
/** thread-safe */
class Crew {

    // START_HIGHLIGHT
  private List<String> members = Collections.synchronizedList(new ArrayList<>());
    // END_HIGHLIGHT

  void add(String name) {
    members.add(name);
  }
}
// AFTER:end