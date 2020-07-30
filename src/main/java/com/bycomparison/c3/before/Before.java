package com.bycomparison.c3.before;

import java.util.HashMap;
import java.util.Map;

public class Before {

}

// BEFORE:start
class Rules {

  static final Map<String, Integer> SCORES = getScores();

  private static Map<String, Integer> getScores() {
    Map<String, Integer> result = new HashMap<>();
    result.put("Simon", 1);
    return result;
  }

}
// BEFORE:end