package com.bycomparison.springboot.favorconstructoroverfieldinjection.after;

public class After {

}

interface OxygenMonitor {

}

@interface Component {

}

@interface Autowired {

}

// AFTER:start
@Component
class SpaceSuit {

  // START_HIGHLIGHT
  private final OxygenMonitor oxygenMonitor;

  public SpaceSuit(OxygenMonitor oxygenMonitor) {
    this.oxygenMonitor = oxygenMonitor;
  }
  // END_HIGHLIGHT
}
// AFTER:end


