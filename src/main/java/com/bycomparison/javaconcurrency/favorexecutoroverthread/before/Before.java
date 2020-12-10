package com.bycomparison.javaconcurrency.favorexecutoroverthread.before;

import java.util.List;

public class Before {

}

interface Job {

  void doWork();
}

// BEFORE:start
class Main {

  static void start(List<Job> jobs) {
    for (Job job : jobs) {
      // START_HIGHLIGHT
      new Thread(job::doWork).start();
      // END_HIGHLIGHT
    }
  }
}
// BEFORE:end