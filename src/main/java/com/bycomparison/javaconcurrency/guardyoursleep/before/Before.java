package com.bycomparison.javaconcurrency.guardyoursleep.before;

import com.bycomparison.javaconcurrency.guardyourwaits.Launch;

public class Before {

}

// BEFORE:start
class MissionControl {

  void initiateLaunch() {
    delay();
    Launch.initiateCountdown();
  }

  private void delay() {
    // START_HIGHLIGHT
    try {
      Thread.sleep(60_000L);
    } catch (InterruptedException ignored) {
    }
    // END_HIGHLIGHT
  }
}
// BEFORE:end

