package com.bycomparison.modernjava.removeunnecessaryinterface.after;

import java.util.NoSuchElementException;
import java.util.Optional;

public class After {

}


// AFTER:start
// START_HIGHLIGHT
class Calculator {
  // END_HIGHLIGHT
  public int add(int x, int y) {
    return x + y;
  }
}
// AFTER:end