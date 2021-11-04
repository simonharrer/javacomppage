package com.bycomparison.modernjava.switchtoswitchexpressions.before;

public class Before {}

// BEFORE:start
class NameBadge {

  enum Rank {
    PILOT,
    COMMANDER,
    RESEARCHER
  }

  String name;
  Rank rank;

  public String toString() {
    // START_HIGHLIGHT
    String result;
    switch (rank) {
      case COMMANDER:
        result = "***";
        break;
      case PILOT:
        result = "**";
        break;
      case RESEARCHER:
      default:
        result = "*";
    }
    return name + " " + result;
    // END_HIGHLIGHT
  }
}
// BEFORE:end
