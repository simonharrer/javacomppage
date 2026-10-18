package com.bycomparison.javaconcurrency.structureyourconcurrenttasks.after;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class After {}

record Weather(boolean clear) {}
record CrewStatus(boolean ready) {}
record LaunchReport(Weather weather, CrewStatus crew) {}

// AFTER:start
class LaunchCheck {

  LaunchReport check() throws InterruptedException {
    // START_HIGHLIGHT
    try (var scope = StructuredTaskScope.open()) {
      Subtask<Weather> weather = scope.fork(this::fetchWeather);
      Subtask<CrewStatus> crew = scope.fork(this::fetchCrew);
      scope.join();
      return new LaunchReport(weather.get(), crew.get());
    }
    // END_HIGHLIGHT
  }

  Weather fetchWeather() {
    return new Weather(true);
  }

  CrewStatus fetchCrew() {
    return new CrewStatus(true);
  }
}
// AFTER:end
