package com.bycomparison.javaconcurrency.guardyoursleep.after;

import com.bycomparison.javaconcurrency.guardyourwaits.Launch;

public class After {

}

// AFTER:start
class MissionControl {

  void initiateLaunch() {
    // START_HIGHLIGHT
    var now = System.currentTimeMillis();
    var sleepDuration = 60_000L;
    var wakeupTime = now + sleepDuration;
    while (wakeupTime < System.currentTimeMillis()) {
      try {
        Thread.sleep(wakeupTime - now);
      } catch (InterruptedException ignored) {
      }
    }
    // END_HIGHLIGHT
    Launch.initiateCountdown();
  }
}
// AFTER:end
