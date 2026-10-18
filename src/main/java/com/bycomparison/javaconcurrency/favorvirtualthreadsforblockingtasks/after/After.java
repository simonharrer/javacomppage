package com.bycomparison.javaconcurrency.favorvirtualthreadsforblockingtasks.after;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class After {}

record Reading(String station, double value) {}

interface Station {
  Reading fetchReading() throws IOException;
}

// AFTER:start
class TelemetryCollector {

  List<Reading> collect(List<Station> stations)
      throws InterruptedException, ExecutionException {
    // START_HIGHLIGHT
    try (var executor =
        Executors.newVirtualThreadPerTaskExecutor()) {
    // END_HIGHLIGHT
      List<Future<Reading>> futures = new ArrayList<>();
      for (Station station : stations) {
        futures.add(executor.submit(station::fetchReading));
      }
      List<Reading> readings = new ArrayList<>();
      for (Future<Reading> future : futures) {
        readings.add(future.get());
      }
      return readings;
    // START_HIGHLIGHT
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end
