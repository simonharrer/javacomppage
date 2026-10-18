package com.bycomparison.modernjava.writedoccommentsinmarkdown.after;

import java.util.List;

public class After {}

// AFTER:start
class Inventory {

  // START_HIGHLIGHT
  /// Returns the supplies that run out before a mission day.
  ///
  /// A supply runs out when its _daily consumption_
  /// times the mission day exceeds its stock.
  ///
  /// - The result is sorted by name.
  /// - An empty list means the stock lasts.
  ///
  /// Example:
  /// ```
  /// var missing = inventory.runningOutBefore(30);
  /// ```
  ///
  /// @param missionDay the day to check, from `1`
  /// @return the supplies that run out, never `null`
  /// @see Supply#dailyConsumption()
  // END_HIGHLIGHT
  List<Supply> runningOutBefore(int missionDay) {
    return List.of();
  }

  record Supply(String name, int stock, int dailyConsumption) {}
}
// AFTER:end
