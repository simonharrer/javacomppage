package com.bycomparison;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public record Category(String name, String text, List<Comparison> comparisons) {

  public String filename() {
    return name.replaceAll(" ", "-").toLowerCase(Locale.ENGLISH) + ".html";
  }

  public String title() {
    return name + " by Comparison";
  }

  public long numberOfComparisons() {
    return comparisons.size();
  }

  public String link() {
    return filename();
  }

  public List<String> groups() {
    return comparisons.stream().map(Comparison::group).distinct().collect(Collectors.toList());
  }

  public List<Comparison> comparisonsForGroup(String group) {
    return comparisons.stream().filter(c -> c.group().equals(group)).collect(Collectors.toList());
  }

}
