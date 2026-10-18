package com.bycomparison.modernjava.writedoccommentsinmarkdown.before;

import java.util.List;

public class Before {}

// BEFORE:start
class Inventory {

  // START_HIGHLIGHT
  /**
   * Returns the supplies that run out before a mission day.
   * <p>
   * A supply runs out when its <em>daily consumption</em>
   * times the mission day exceeds its stock.
   * <ul>
   *   <li>The result is sorted by name.</li>
   *   <li>An empty list means the stock lasts.</li>
   * </ul>
   * <p>
   * Example:
   * <pre>{@code
   * var missing = inventory.runningOutBefore(30);
   * }</pre>
   *
   * @param missionDay the day to check, from {@code 1}
   * @return the supplies that run out, never {@code null}
   * @see Supply#dailyConsumption()
   */
  // END_HIGHLIGHT
  List<Supply> runningOutBefore(int missionDay) {
    return List.of();
  }

  record Supply(String name, int stock, int dailyConsumption) {}
}
// BEFORE:end
