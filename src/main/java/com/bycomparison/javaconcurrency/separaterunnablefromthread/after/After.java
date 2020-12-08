package com.bycomparison.javaconcurrency.separaterunnablefromthread.after;

public class After {

}

// AFTER:start
class Countdown implements Runnable {

  int start;
  boolean finished;

  Countdown(int start) {
    this.start = start;
    this.finished = false;
  }

  public void run() {
    while (start > 0) {
      start--;
    }
    finished = true;
  }
}

class Main {
  public static void main(String[] args) {
    var countdown = new Countdown(10);
    new Thread(countdown).start();
  }
}
// AFTER:end
