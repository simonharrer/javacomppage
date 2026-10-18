package com.bycomparison.javaconcurrency.structureyourconcurrenttasks.before;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Before {}

record Weather(boolean clear) {}
record CrewStatus(boolean ready) {}
record LaunchReport(Weather weather, CrewStatus crew) {}

// BEFORE:start
class LaunchCheck {

  private final ExecutorService executor =
      Executors.newVirtualThreadPerTaskExecutor();

  LaunchReport check()
      throws InterruptedException, ExecutionException {
    // START_HIGHLIGHT
    Future<Weather> weather = executor.submit(this::fetchWeather);
    Future<CrewStatus> crew = executor.submit(this::fetchCrew);
    try {
      return new LaunchReport(weather.get(), crew.get());
    } catch (InterruptedException | ExecutionException e) {
      weather.cancel(true);
      crew.cancel(true);
      throw e;
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
// BEFORE:end
