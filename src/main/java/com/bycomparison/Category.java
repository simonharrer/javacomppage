package com.bycomparison;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public record Category(String name, String text, List<Comparison> comparisons) {

  public String filename() {
    return uniqueName() + ".html";
  }

  public String uniqueName() {
    return name.replaceAll(" ", "-").toLowerCase(Locale.ENGLISH);
  }

  public String title() {
    return name + " by Comparison";
  }

  public String previewCard() {
    return "https://java.by-comparison.com/img/cover.jpg";
  }

  public String link() {
    return filename();
  }

  public List<String> groups() {
    return comparisons.stream().map(Comparison::group).distinct().collect(Collectors.toList());
  }

  public String htmlText() {
    return Markdown.toHtml(text);
  }

  public List<Comparison> comparisonsForGroup(String group) {
    return comparisons.stream().filter(c -> c.group().equals(group)).collect(Collectors.toList());
  }

}
