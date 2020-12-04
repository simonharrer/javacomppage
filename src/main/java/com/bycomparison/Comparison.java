package com.bycomparison;

import java.util.List;
import java.util.Locale;

public record Comparison(String name, String group, String category, List<String>tags, String beforeCode,
                         String beforeCodeHighlight, String beforeText,
                         String afterCode, String afterCodeHighlight, String afterText,
                         String language, String sources, boolean published, boolean book, String sample) {

  public String filename() {
    return uniqueName() + ".html";
  }

  private String uniqueName() {
    return name.replaceAll(" ", "-").replaceAll("/", "-").toLowerCase(Locale.ENGLISH);
  }

  public String title() {
    return name + " | " + new Category(category, "", List.of(this)).title(); //Fixme: What should the 2nd param be?!
  }

  public String diff() {
    return Diff.withoutPreabmle(this);
  }

  public String link() {
    return uniqueName() + ".html";
  }
}
