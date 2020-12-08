package com.bycomparison.javaconcurrency.separaterunnablefromthread.before;

public class Before {

}

// BEFORE:start
class Countdown extends Thread {

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
    var thread = new Countdown(10);
    thread.start();
    thread.join();
  }
}
// BEFORE:end