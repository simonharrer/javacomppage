package com.bycomparison.c4.after;

import com.bycomparison.c4.Launch;

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
