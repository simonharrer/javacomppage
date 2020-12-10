package com.bycomparison.javaconcurrency.separaterunnablefromthread.after;

public class After {

}

// AFTER:start
// START_HIGHLIGHT
class Countdown implements Runnable {
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
  public static void main(String[] args) {
    // START_HIGHLIGHT
    Runnable countdown = new Countdown(10);
    new Thread(countdown).start();
    // END_HIGHLIGHT
  }
}
// AFTER:end
