package com.bycomparison.modernjava.switchtoswitchexpressions.after;

public class After {

}


// AFTER:start
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
    return name + " " + switch (rank) {
      case COMMANDER -> "***";
      case PILOT -> "**";
      case RESEARCHER -> "*";
    };
    // END_HIGHLIGHT
  }
}
// AFTER:end