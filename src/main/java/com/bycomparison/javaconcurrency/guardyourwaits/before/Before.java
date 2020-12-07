package com.bycomparison.javaconcurrency.guardyourwaits.before;

import com.bycomparison.javaconcurrency.guardyourwaits.Launch;

public class Before {

}

// BEFORE:start
class MissionControl {
    boolean isReadyToLaunch = false;

    void initiateLaunch() {
        while (!isReadyToLaunch) {
            // standby
        }
        Launch.initiateCountdown();
    }
}
// BEFORE:end

