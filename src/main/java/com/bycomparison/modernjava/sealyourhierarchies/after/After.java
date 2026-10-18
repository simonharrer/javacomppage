package com.bycomparison.modernjava.sealyourhierarchies.after;

public class After {}

// AFTER:start
// START_HIGHLIGHT
sealed interface Cargo permits Food, Fuel, Oxygen {}
// END_HIGHLIGHT

record Food(int rations) implements Cargo {}
record Fuel(double liters) implements Cargo {}
record Oxygen(int tanks) implements Cargo {}

class LoadPlanner {

  double weightInKg(Cargo cargo) {
    // START_HIGHLIGHT
    return switch (cargo) {
      case Food food -> food.rations() * 0.8;
      case Fuel fuel -> fuel.liters() * 0.82;
      case Oxygen oxygen -> oxygen.tanks() * 15.0;
    };
    // END_HIGHLIGHT
  }
}
// AFTER:end
