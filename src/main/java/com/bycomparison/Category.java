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

  public String pageTitle() {
    if ("Java".equals(name)) {
      return "All 70 Comparisons of the Book Java by Comparison | Clean Java Code";
    }
    return title() + ": Before/After Code Examples";
  }

  public String description() {
    if ("Java".equals(name)) {
      return "Table of contents of Java by Comparison: all 70 before/after comparisons for clean Java code, from booleans and naming to exceptions, JUnit 5, and streams.";
    }
    return "%s: %d comparisons of code before and after the refinement, with explanations, by the authors of Java by Comparison."
        .formatted(title(), comparisons.size());
  }

  public String canonical() {
    return "https://java.by-comparison.com/site/" + filename();
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

  public Category withComparisons(List<Comparison> comparisons) {
    return new Category(name, text, comparisons);
  }

}
