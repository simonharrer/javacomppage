package com.bycomparison.javaconcurrency.makesuretocleanupyourexecutor.before;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Before {

}

interface Job {

  void doWork();
}

// BEFORE:start
class Main {

  static void start(List<Job> jobs) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (Job job : jobs) {
      executorService.execute(job::doWork);
    }
    executorService.shutdown();
  }
}
// BEFORE:end