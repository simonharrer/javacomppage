package com.bycomparison;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public record Comparison(String name, String group, String category, List<String> tags,
                         String beforeCode,
                         String beforeCodeHighlight, String beforeText,
                         String afterCode, String afterCodeHighlight, String afterText,
                         String language, String sources, boolean published, boolean book,
                         String sample) {

  public static Comparison javaComparison(String name, String group, List<String> tags,
      String beforeText, String afterText,
      String category) {
    var codeDirectory = normalize(category) + "/" + normalize(name);
    return new Comparison(name, group, category,
        tags,
        Code.parseBeforeCodePart(codeDirectory).code(),
        Code.parseBeforeCodePart(codeDirectory).highlightedLines().stream().map(Object::toString)
            .collect(
                Collectors.joining(",")), Markdown.toHtml(beforeText),
        Code.parseAfterCodePart(codeDirectory).code(),
        Code.parseAfterCodePart(codeDirectory).highlightedLines().stream().map(Object::toString)
            .collect(
                Collectors.joining(",")), Markdown.toHtml(afterText),
        "java", "",
        true, false, "");
  }

  public static Comparison empty(String name, String category) {
    return new Comparison(name, "Group", category,
        List.of(),
        "", "", "",
        "", "", "",
        "java", "",
        true, false, "");
  }

  public static Comparison empty(String name, String group, String category) {
    return new Comparison(name, group, category,
        List.of(),
        "", "", "",
        "", "", "",
        "java", "",
        true, false, "");
  }

  private static String normalize(String name) {
    return name
        .replaceAll("[^A-Za-z]", "").toLowerCase(
            Locale.ROOT);
  }

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
    // TODO preview for java by comparison items
    return "https://java.by-comparison.com/site/" + previewPngFilename();
  }

  public String diff() {
    return Diff.withoutPreabmle(this);
  }

  public String link() {
    return uniqueName() + ".html";
  }

  public void generatePreviewCard(Path site) throws IOException {
    Carbon.generatePreviewCard(site, beforeCode, uniqueName(), previewFilename(),
        previewPngFilename());
  }

  private String previewPngFilename() {
    return uniqueName() + beforeCode.hashCode() + ".png";
  }

  private String previewFilename() {
    return uniqueName() + beforeCode.hashCode();
  }
}
