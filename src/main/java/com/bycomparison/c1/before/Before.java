package com.bycomparison.c1.before;

public class Before {



}

interface OxygenMonitor {}
@interface Component {}
@interface Autowired {}

// BEFORE:start
@Component
class SpaceSuit {

  @Autowired
  private OxygenMonitor oxygenMonitor;
}
// BEFORE:end



