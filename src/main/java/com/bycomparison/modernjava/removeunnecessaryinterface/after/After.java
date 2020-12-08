package com.bycomparison.modernjava.removeunnecessaryinterface.after;

import java.util.NoSuchElementException;
import java.util.Optional;

public class After {

}


// AFTER:start
class Calculator {
  public int add(int x, int y) {
    return x + y;
  }
}
// AFTER:end