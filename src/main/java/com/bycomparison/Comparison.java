package com.bycomparison;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public record Comparison(String name, String group, String category, List<String> tags,
                         String beforeCode,
                         String beforeCodeHighlight, String beforeText,
                         String afterCode, String afterCodeHighlight, String afterText,
                         String language, String sources, boolean published, boolean book,
                         String sample) {

  public String filename() {
    return uniqueName() + ".html";
  }

  private String uniqueName() {
    return name.replaceAll(" ", "-").replaceAll("/", "-").toLowerCase(Locale.ENGLISH);
  }

  public String title() {
    return name + " | " + categoryTitle(category);
  }

  private static String categoryTitle(String categoryName) {
    return new Category(categoryName, "", List.of()).title();
  }

  public String previewCard() {
    return "https://java.by-comparison.com/site/" + previewPngFilename();
  }

  public String diff() {
    return Diff.withoutPreabmle(this);
  }

  public String link() {
    return uniqueName() + ".html";
  }

  public void generatePreviewCard(Path site) throws IOException {
    Carbon.generatePreviewCard(site, beforeCode, uniqueName(), previewPngFilename());
  }

  private String previewPngFilename() {
    return uniqueName() + beforeCode.hashCode() + ".png";
  }
}
