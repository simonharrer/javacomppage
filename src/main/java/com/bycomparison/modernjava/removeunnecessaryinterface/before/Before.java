package com.bycomparison.modernjava.removeunnecessaryinterface.before;

public class Before {

}

// BEFORE:start
interface Calculator {
  int add(int x, int y);
}

class CalculatorImpl implements Calculator {

  @Override
  public int add(int x, int y) {
    return x + y;
  }
}
// BEFORE:end