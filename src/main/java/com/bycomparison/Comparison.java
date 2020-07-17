package com.bycomparison;

import java.util.List;
import java.util.Locale;

public record Comparison(String name, String category, List<String>tags, String beforeCode,
                         String beforeCodeHighlight, String beforeText,
                         String afterCode, String afterCodeHighlight, String afterText) {

  public String filename() {
    return name.replaceAll(" ", "-").toLowerCase(Locale.ENGLISH) + ".html";
  }

  public String title() {
    return name + " | " + new Category(category, List.of(this)).title();
  }

  public String diff() {
    return Diff.of(this);
  }
}
