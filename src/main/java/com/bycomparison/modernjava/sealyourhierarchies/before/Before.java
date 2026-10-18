package com.bycomparison.modernjava.sealyourhierarchies.before;

public class Before {}

// BEFORE:start
// START_HIGHLIGHT
interface Cargo {}
// END_HIGHLIGHT

record Food(int rations) implements Cargo {}
record Fuel(double liters) implements Cargo {}
record Oxygen(int tanks) implements Cargo {}

class LoadPlanner {

  double weightInKg(Cargo cargo) {
    // START_HIGHLIGHT
    if (cargo instanceof Food food) {
      return food.rations() * 0.8;
    } else if (cargo instanceof Fuel fuel) {
      return fuel.liters() * 0.82;
    } else if (cargo instanceof Oxygen oxygen) {
      return oxygen.tanks() * 15.0;
    }
    throw new IllegalArgumentException("Unknown cargo " + cargo);
    // END_HIGHLIGHT
  }
}
// BEFORE:end
