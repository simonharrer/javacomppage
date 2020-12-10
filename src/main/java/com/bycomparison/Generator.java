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

    comparisons.forEach(comparison -> renderToFile(comparison, comparisons, categories, site));
    categories.forEach(category -> renderToFile(category, categories, site));
    renderMethod(categories, site);
    renderIndex(categories, site);
  }

  private static void renderMethod(List<Category> categories, Path site) {
    renderToFile("method.html", views.method.template(categories,
        new Comparison("Actionable Name Starting With a Strong Verb", "", "method", List.of("example-tag"),
            """
                // bad code
                int x = 3;
                // problematic lines are highlighted
                // imports and accessibility modifier left out
                """, "2", "Why the code above is hard to read",
            """
                // better code
                // it's not good or clean code, just cleaner or better code
                int remainingChocolateBars = 3;
                // better lines are highlighted
                """, "2",
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

  private static void renderToFile(Comparison comparison,
      List<Comparison> comparisons, List<Category> categories, Path site) {
    generatePreviewCard(comparison, site);

    var dependencies = getDependencies(comparison, comparisons);

    renderToFile(comparison.filename(),
        views.comparison.template(comparison, dependencies, categories), site);
  }

  private static Dependencies getDependencies(Comparison comparison, List<Comparison> comparisons) {
    var next = "";
    var nextName = "";
    var previous = "";
    var previousName = "";
    var comparisonsInSameCategoryAndGroup = comparisons.stream()
        .filter(c -> c.category().equals(comparison.category()))
        .filter(c -> c.group().equals(comparison.group())).collect(Collectors.toList());
    if (comparisonsInSameCategoryAndGroup.size() > 1) {
      var index = comparisonsInSameCategoryAndGroup.indexOf(comparison);
      if (index < comparisonsInSameCategoryAndGroup.size() - 1) {
        var nextComparison = comparisonsInSameCategoryAndGroup.get(index + 1);
        next = nextComparison.link();
        nextName = nextComparison.name();
      }
      if (index > 0) {
        var previousComparison = comparisonsInSameCategoryAndGroup.get(index - 1);
        previous = previousComparison.link();
        previousName = previousComparison.name();
      }
    }
    return new Dependencies(next, nextName, previous, previousName);
  }

  private static void generatePreviewCard(Comparison comparison, Path site) {
    if (!comparison.beforeCode().isBlank()) {
      try {
        comparison.generatePreviewCard(site);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
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
