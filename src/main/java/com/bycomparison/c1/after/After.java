package com.bycomparison.c1.after;

public class After {

}

interface OxygenMonitor {}

@interface Component {}

@interface Autowired {}

// AFTER:start
@Component
class SpaceSuit {
  private final OxygenMonitor oxygenMonitor;

  public SpaceSuit(OxygenMonitor oxygenMonitor) {
        this.oxygenMonitor = oxygenMonitor;
    }
}
// AFTER:end


