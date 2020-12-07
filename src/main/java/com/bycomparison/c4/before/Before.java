package com.bycomparison.c4.before;

import com.bycomparison.c4.Launch;

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

