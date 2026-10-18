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

  static void deleteWebsite(GeneratorConfiguration configuration) throws IOException {
    Files.createDirectories(configuration.site());

    Files.walk(configuration.site())
        .sorted(Comparator.reverseOrder())
        .filter(path -> path.toString().endsWith(".html"))
        .map(Path::toFile)
        .forEach(File::delete);
  }

  static void generateWebsite(GeneratorConfiguration configuration) throws IOException {
    var comparisons = configuration.published() ? getPublishedComparisons() : getComparisons();
    var categories = getCategories(comparisons);

    generate(configuration, comparisons, categories);
  }

  private static void generate(
      GeneratorConfiguration configuration, List<Comparison> comparisons, List<Category> categories)
      throws IOException {
    Files.createDirectories(configuration.site());
    System.out.println("Comparisons: " + comparisons.size());
    System.out.println("Categories: " + categories.size());

    comparisons.forEach(
        comparison -> renderToFile(comparison, comparisons, categories, configuration));
    categories.forEach(category -> renderToFile(category, categories, configuration.site()));
    // renderMethod(categories, configuration.site());
    renderWelcome(categories, configuration.site());
  }

  private static void renderMethod(List<Category> categories, Path site) {
    renderToFile(
        "method.html",
        views.comparison.template(getExampleComparison(), Dependencies.NO_DEPENDENCIES, categories),
        site);
  }

  private static Comparison getExampleComparison() {
    var beforeCode =
        """
        // BEFORE:start
        class Container {
          // bad code
          // START_HIGHLIGHT
          int x = 3;
          // END_HIGHLIGHT
          // problematic lines are highlighted
          // imports and accessibility modifier left out
        }
        // BEFORE:end
        """;
    var afterCode =
        """
        // AFTER:start
        class SweetsDrawer {
          // better code
          // it's not good or clean code, just cleaner or better code
          // START_HIGHLIGHT
          int chocolateBars = 3;
          // END_HIGHLIGHT
          // better lines are highlighted
        }
        // AFTER:end
        """;
    return new Comparison(
        "Actionable Name Starting With a Strong Verb",
        "",
        "method",
        List.of("example-tag"),
        Code.parseBeforeCode(beforeCode).code(),
        Code.parseBeforeCode(beforeCode).highlight(),
        "Why the code above is hard to read",
        Code.parseAfterCode(afterCode).code(),
        Code.parseAfterCode(afterCode).highlight(),
        "Why the code on the green / right-hand side is cleaner and more maintainable",
        "java",
        "",
        true,
        false,
        "");
  }

  private static void renderWelcome(List<Category> categories, Path site) {
    renderToFile("welcome.html", views.welcome.template(categories), site);
  }

  private static List<Category> getCategories(List<Comparison> comparisons) {
    var categoryNameToComparisons =
        comparisons.stream().collect(Collectors.groupingBy(Comparison::category));
    return Categories.categories().stream()
        .map(e -> e.withComparisons(categoryNameToComparisons.get(e.name())))
        .filter(c -> c.comparisons() != null && !c.comparisons().isEmpty())
        .collect(Collectors.toList());
  }

  private static List<Comparison> getPublishedComparisons() {
    return getComparisons().stream().filter(Comparison::published).collect(Collectors.toList());
  }

  private static List<Comparison> getComparisons() {
    List<Comparison> comparisons = new ArrayList<>();
    comparisons.addAll(ComparisonsJava.getComparisons());
    comparisons.addAll(ComparisonsModernJava.chapter1NewLanguageFeatures());
    comparisons.addAll(unpublish(ComparisonsModernJava.getComparisons()));
    comparisons.addAll(ComparisonsJavaConcurrency.chapter1ProtectYourSharedMutableState());
    comparisons.addAll(ComparisonsJavaConcurrency.chapter2ProtectYourSharedMutableCollection());
    comparisons.addAll(unpublish(ComparisonsJavaConcurrency.chapter3SolveYourProblemInParallel()));
    comparisons.addAll(unpublish(ComparisonsSpringBoot.getComparisons()));
    return comparisons;
  }

  private static List<Comparison> unpublish(List<Comparison> comparisons) {
    return comparisons.stream().map(Comparison::unpublish).collect(Collectors.toList());
  }

  private static void renderToFile(
      Comparison comparison,
      List<Comparison> comparisons,
      List<Category> categories,
      GeneratorConfiguration configuration) {
    if (configuration.preview()) {
      generatePreviewCard(comparison, configuration.site());
    }

    var dependencies = getDependencies(comparison, comparisons);

    renderToFile(
        comparison.filename(),
        views.comparison.template(comparison, dependencies, categories),
        configuration.site());
  }

  private static Dependencies getDependencies(Comparison comparison, List<Comparison> comparisons) {
    var next = "";
    var nextName = "";
    var previous = "";
    var previousName = "";
    var comparisonsInSameCategoryAndGroup =
        comparisons.stream()
            .filter(c -> c.category().equals(comparison.category()))
            .filter(c -> c.group().equals(comparison.group()))
            .collect(Collectors.toList());
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
    renderToFile(category.filename(), views.category.template(category, categories), site);
  }

  private static void renderToFile(String filename, RockerModel template, Path site) {
    try {
      Files.write(site.resolve(filename), template.render().toString().getBytes());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
