package com.bycomparison;

import java.util.List;
import java.util.Locale;

public record Category(String name, List<Comparison>comparisons) {

  public String filename() {
    return name.replaceAll(" ", "-").toLowerCase(Locale.ENGLISH) + ".html";
  }

  public String title() {
    return name + " by Comparison";
  }

  public long numberOfComparisons() {
    return comparisons.size();
  }

}
