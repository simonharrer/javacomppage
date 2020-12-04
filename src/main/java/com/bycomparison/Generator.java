package com.bycomparison;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fizzed.rocker.RockerModel;

public class Generator {

  Map<Comparison,String> generatedCodeCards = new HashMap<>();

  public static final String DIR = "site/";

  static void deleteWebsite() throws IOException {
    var dir = Path.of(DIR);
    Files.createDirectories(dir);

    Files.walk(dir)
        .sorted(Comparator.reverseOrder())
        .filter(path -> path.endsWith(".html"))
        .map(Path::toFile)
        .forEach(File::delete);
  }

  static void generateWebsite() throws IOException {
    Files.createDirectories(Path.of(DIR));

    var comparisons = getPublishedComparisons();
    var categories = categories(comparisons);

    System.out.println("Comparisons: " + comparisons.size());
    System.out.println("Categories: " + categories.size());

    comparisons.forEach(comparison -> renderToFile(comparison, categories));
    categories.forEach(category -> renderToFile(category, categories));
    renderMethod(categories);
    renderIndex(categories);
  }

  private static void renderMethod(List<Category> categories) {
    renderToFile("method.html", views.method.template(categories,
        new Comparison("Actionable Name", "method", List.of("example-tag"), "// bad code\nint x = 3;", "1", "Why the code above is hard to read", "// better code\nint remainingChocolateBars = 3;", "1", "Why the code on the green / right-hand side is cleaner and more maintainable",
            "java", "", true, false, "")));
  }

  private static void renderIndex(List<Category> categories) {
    renderToFile("index.html", views.index.template(categories));
  }

  private static List<Category> categories(List<Comparison> publishedComparisons) {
    return publishedComparisons.stream().collect(Collectors.groupingBy(Comparison::category))
        .entrySet().stream().map(e -> new Category(e.getKey(), e.getValue()))
        .collect(Collectors.toList());
  }

  private static List<Comparison> getPublishedComparisons() {
    return Stream
        .concat(Comparisons.getComparisons().stream(),
            BookComparisons.getBookComparisons().stream())
        .filter(Comparison::published)
        .collect(Collectors.toList());
  }

  private static void renderToFile(Comparison comparison, List<Category> categories) {
    try {
      comparison.generatePreviewCard();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    renderToFile(comparison.filename(),
            views.comparison.template(comparison, categories));
  }

  private static void renderToFile(Category category, List<Category> categories) {
    renderToFile(category.filename(),
        views.category.template(category, categories));
  }

  private static void renderToFile(String filename, RockerModel template) {
    try {
      Files.write(Path.of(DIR + filename),
          template
              .render()
              .toString().getBytes());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
