package com.bycomparison.javaconcurrency.passcontextwithscopedvalues.after;

public class After {}

record Command(String name) {}

record Astronaut(String name) {
  boolean mayExecute(Command command) {
    return true;
  }
}

// AFTER:start
class MissionControl {

  // START_HIGHLIGHT
  static final ScopedValue<Astronaut> CURRENT =
      ScopedValue.newInstance();
  // END_HIGHLIGHT

  private final Dispatcher dispatcher = new Dispatcher();

  void handle(Astronaut astronaut, Command command) {
    // START_HIGHLIGHT
    ScopedValue.where(CURRENT, astronaut)
        .run(() -> dispatcher.execute(command));
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
// AFTER:end
