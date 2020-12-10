package com.bycomparison.javaconcurrency.makesuretocleanupyourexecutor.after;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class After {

}


interface Job {

  void doWork();
}


// AFTER:start
class Main {

  static void start(List<Job> jobs) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    // START_HIGHLIGHT
    try {
      // END_HIGHLIGHT
      for (Job job : jobs) {
        executorService.execute(job::doWork);
      }
      // START_HIGHLIGHT
    } finally {
      executorService.shutdown();
    }
    // END_HIGHLIGHT
  }
}
// AFTER:end
