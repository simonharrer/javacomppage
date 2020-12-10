package com.bycomparison.modernjava.removeunnecessaryinterface.before;

public class Before {

}

// BEFORE:start
interface Calculator {
  int add(int x, int y);
}

// START_HIGHLIGHT
class CalculatorImpl implements Calculator {
  // END_HIGHLIGHT
  @Override
  public int add(int x, int y) {
    return x + y;
  }
}
// BEFORE:end