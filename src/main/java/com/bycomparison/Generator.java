package com.bycomparison;

import com.fizzed.rocker.RockerModel;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Generator {

  public static final String DIR = "site/";

  static void deleteWebsite(Path site) throws IOException {
    Files.createDirectories(site);

    Files.walk(site)
        .sorted(Comparator.reverseOrder())
        .filter(path -> path.endsWith(".html"))
        .map(Path::toFile)
        .forEach(File::delete);
  }

  static void generateWebsite(Path site) throws IOException {
    Files.createDirectories(site);

    var comparisons = getPublishedComparisons();
    var categories = getPublishedCategories(comparisons);

    System.out.println("Comparisons: " + comparisons.size());
    System.out.println("Categories: " + categories.size());

    comparisons.forEach(comparison -> renderToFile(comparison, categories, site));
    categories.forEach(category -> renderToFile(category, categories, site));
    renderMethod(categories, site);
    renderIndex(categories, site);
  }

  private static void renderMethod(List<Category> categories, Path site) {
    renderToFile("method.html", views.method.template(categories,
        new Comparison("Actionable Name", "", "method", List.of("example-tag"),
            "// bad code\nint x = 3;", "1", "Why the code above is hard to read",
            "// better code\nint remainingChocolateBars = 3;", "1",
            "Why the code on the green / right-hand side is cleaner and more maintainable",
            "java", "", true, false, "")), site);
  }

  private static void renderIndex(List<Category> categories, Path site) {
    renderToFile("index.html", views.index.template(categories), site);
  }

  private static List<Category> getPublishedCategories(List<Comparison> publishedComparisons) {
    var categoryNameToComparisons = publishedComparisons.stream()
        .collect(Collectors.groupingBy(Comparison::category));
    return Categories.categories().stream()
        .map(e -> new Category(e.name(), e.text(), categoryNameToComparisons.get(e.name())))
        .filter(c -> !c.comparisons().isEmpty())
        .collect(Collectors.toList());
  }

  private static List<Comparison> getPublishedComparisons() {
    List<Comparison> comparisons = new ArrayList<>();
    comparisons.addAll(ComparisonsJava.getComparisons());
    comparisons.addAll(ComparisonsModernJava.getComparisons());
    comparisons.addAll(ComparisonsJavaConcurrency.getComparisons());
    comparisons.addAll(ComparisonsSpringBoot.getComparisons());

    return comparisons.stream()
        .filter(Comparison::published)
        .collect(Collectors.toList());
  }

  private static void renderToFile(Comparison comparison, List<Category> categories, Path site) {
    if (!comparison.beforeCode().isBlank()) {
      try {
        comparison.generatePreviewCard(site);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
    renderToFile(comparison.filename(),
        views.comparison.template(comparison, categories), site);
  }

  private static void renderToFile(Category category, List<Category> categories, Path site) {
    renderToFile(category.filename(),
        views.category.template(category, categories), site);
  }

  private static void renderToFile(String filename, RockerModel template, Path site) {
    try {
      Files.write(site.resolve(filename),
          template
              .render()
              .toString().getBytes());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
