package com.bycomparison.javaconcurrency.separaterunnablefromthread.before;

public class Before {

}

// BEFORE:start
// START_HIGHLIGHT
class Countdown extends Thread {
  // END_HIGHLIGHT

  int start;
  boolean finished;

  Countdown(int start) {
    this.start = start;
    this.finished = false;
  }

  @Override
  public void run() {
    while (start > 0) {
      start--;
    }
    finished = true;
  }
}

class Main {
  public static void main(String[] args) throws InterruptedException {
    // START_HIGHLIGHT
    Thread thread = new Countdown(10);
    thread.start();
    // END_HIGHLIGHT
  }
}
// BEFORE:end