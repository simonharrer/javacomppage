package com.bycomparison.javaconcurrency.guardyourwaits.after;

import com.bycomparison.javaconcurrency.guardyourwaits.Launch;

public class After {

}

// AFTER:start
class MissionControl {
    boolean isReadyToLaunch = false;

    synchronized void initiateLaunch() {
        while (!isReadyToLaunch) {
            try {
                wait(); // standby
            } catch (InterruptedException e) {
            }
        }
        Launch.initiateCountdown();
    }
}
// AFTER:end
