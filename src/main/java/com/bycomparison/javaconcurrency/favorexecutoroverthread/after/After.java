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
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (Job job : jobs) {
      executorService.execute(job::doWork);
    }
    executorService.shutdown();
  }
}
// AFTER:end
