package com.bycomparison.javaconcurrency.passcontextwithscopedvalues.before;

public class Before {}

record Command(String name) {}

record Astronaut(String name) {
  boolean mayExecute(Command command) {
    return true;
  }
}

// BEFORE:start
class MissionControl {

  // START_HIGHLIGHT
  static final ThreadLocal<Astronaut> CURRENT =
      new ThreadLocal<>();
  // END_HIGHLIGHT

  private final Dispatcher dispatcher = new Dispatcher();

  void handle(Astronaut astronaut, Command command) {
    // START_HIGHLIGHT
    CURRENT.set(astronaut);
    try {
      dispatcher.execute(command);
    } finally {
      CURRENT.remove();
    }
    // END_HIGHLIGHT
  }
}

class Dispatcher {

  void execute(Command command) {
    var astronaut = MissionControl.CURRENT.get();
    if (!astronaut.mayExecute(command)) {
      throw new SecurityException("not allowed: " + command);
    }
    // ...
  }
}
// BEFORE:end
