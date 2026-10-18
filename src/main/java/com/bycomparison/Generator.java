package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {

  private static final String PRISM = "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0";

  private static final String PRISM_HEAD = """
      <link rel="stylesheet" href="%1$s/themes/prism.min.css">
      <link rel="stylesheet" href="%1$s/plugins/line-highlight/prism-line-highlight.min.css">
      """.formatted(PRISM);

  private static final String PRISM_SCRIPTS = """
      <script src="%1$s/prism.min.js"></script>
      <script src="%1$s/components/prism-java.min.js"></script>
      <script src="%1$s/components/prism-properties.min.js"></script>
      <script src="%1$s/plugins/line-highlight/prism-line-highlight.min.js"></script>
      """.formatted(PRISM);

  /** Builds the complete website into the output directory of the configuration. */
  static void generateWebsite(GeneratorConfiguration configuration) throws IOException {
    var output = configuration.output();
    deleteDirectory(output);
    copyDirectory(configuration.staticFiles(), output);

    List<Page> pages = new ArrayList<>(Pages.load(configuration.pages()));
    var comparisons = configuration.published() ? getPublishedComparisons() : getComparisons();
    var categories = getCategories(comparisons);
    System.out.println("Pages: " + pages.size());
    System.out.println("Comparisons: " + comparisons.size());
    System.out.println("Categories: " + categories.size());

    if (configuration.preview()) {
      comparisons.forEach(c -> generatePreviewCard(c, configuration.staticFiles().resolve("site")));
    }
    comparisons.forEach(c -> pages.add(comparisonPage(c, comparisons, categories)));
    categories.forEach(c -> pages.add(categoryPage(c, categories)));
    pages.add(welcomePage(categories));

    pages.forEach(page -> write(output.resolve(page.output()), views.layout.template(page).render().toString()));
    write(output.resolve("sitemap.xml"), Sitemap.render(pages));
  }

  private static Page comparisonPage(Comparison comparison, List<Comparison> comparisons, List<Category> categories) {
    var body = views.comparison.template(comparison, getDependencies(comparison, comparisons), categories).render().toString();
    return new Page("site/" + comparison.filename(), comparison.title(), comparison.description(),
        "/site/" + comparison.filename(), "article", "", "", comparison.previewCard(), "",
        comparison.book() ? "" : PRISM_HEAD, body, comparison.book() ? "" : PRISM_SCRIPTS);
  }

  private static Page categoryPage(Category category, List<Category> categories) {
    var body = views.category.template(category, categories).render().toString();
    return new Page("site/" + category.filename(), category.pageTitle(), category.description(),
        "/site/" + category.filename(), "website", "", "", category.previewCard(), "", "", body, "");
  }

  private static Page welcomePage(List<Category> categories) {
    var body = views.welcome.template(categories).render().toString();
    return new Page("site/welcome.html", "Clean Code by Comparison: Before/After Code Examples | Java by Comparison",
        "Learn clean code by comparing code before and after a refinement: browse all comparisons from the book Java by Comparison and on Java concurrency and modern Java.",
        "/site/welcome.html", "website", "", "", "", "", "", body, "");
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

  private static void generatePreviewCard(Comparison comparison, Path folder) {
    if (!comparison.beforeCode().isBlank()) {
      try {
        comparison.generatePreviewCard(folder);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  private static void write(Path file, String content) {
    try {
      Files.createDirectories(file.getParent());
      Files.writeString(file, content);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static void deleteDirectory(Path directory) throws IOException {
    if (!Files.exists(directory)) {
      return;
    }
    try (Stream<Path> paths = Files.walk(directory)) {
      paths.sorted(Comparator.reverseOrder()).forEach(path -> {
        try {
          Files.delete(path);
        } catch (IOException e) {
          throw new UncheckedIOException(e);
        }
      });
    }
  }

  private static void copyDirectory(Path source, Path target) throws IOException {
    try (Stream<Path> paths = Files.walk(source)) {
      for (var path : paths.toList()) {
        var destination = target.resolve(source.relativize(path).toString());
        if (Files.isDirectory(path)) {
          Files.createDirectories(destination);
        } else {
          Files.copy(path, destination);
        }
      }
    }
  }
}
