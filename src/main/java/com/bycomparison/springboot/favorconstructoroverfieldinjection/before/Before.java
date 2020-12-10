package com.bycomparison.springboot.favorconstructoroverfieldinjection.before;

public class Before {



}

interface OxygenMonitor {}
@interface Component {}
@interface Autowired {}

// BEFORE:start
@Component
class SpaceSuit {

  // START_HIGHLIGHT
  @Autowired
  private OxygenMonitor oxygenMonitor;
  // END_HIGHLIGHT
}
// BEFORE:end



