package com.bycomparison.javaconcurrency.favorexecutoroverthread.after;

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
    // START_HIGHLIGHT
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    // END_HIGHLIGHT
    for (Job job : jobs) {
      // START_HIGHLIGHT
      executorService.execute(job::doWork);
      // END_HIGHLIGHT
    }
    // START_HIGHLIGHT
    executorService.shutdown();
    // END_HIGHLIGHT
  }
}
// AFTER:end
