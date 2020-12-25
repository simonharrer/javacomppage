package com.bycomparison.javaconcurrency.favorexecutoroverthread.after;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class After {

}


interface Job {

  void doWork();
}


// AFTER:start
class Main {

  static void start(List<Job> jobs) {
    // START_HIGHLIGHT
    Executor executor = ForkJoinPool.commonPool();
    // END_HIGHLIGHT
    for (Job job : jobs) {
      // START_HIGHLIGHT
      executor.execute(job::doWork);
      // END_HIGHLIGHT
    }
  }
}
// AFTER:end
