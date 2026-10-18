package com.bycomparison.javaconcurrency.favorvirtualthreadsforblockingtasks.before;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Before {}

record Reading(String station, double value) {}

interface Station {
  Reading fetchReading() throws IOException;
}

// BEFORE:start
class TelemetryCollector {

  List<Reading> collect(List<Station> stations)
      throws InterruptedException, ExecutionException {
    // START_HIGHLIGHT
    ExecutorService executor = Executors.newFixedThreadPool(100);
    try {
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
    } finally {
      executor.shutdown();
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end
