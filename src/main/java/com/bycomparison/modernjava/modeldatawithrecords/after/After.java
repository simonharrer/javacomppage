package com.bycomparison.modernjava.modeldatawithrecords.after;

public class After {}

// AFTER:start
// START_HIGHLIGHT
record Coordinates(double latitude, double longitude) {

  Coordinates {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("bad latitude");
    }
  }
}
// END_HIGHLIGHT
// AFTER:end
