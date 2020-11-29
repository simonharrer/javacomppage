package com.bycomparison;

import com.fizzed.rocker.RockerModel;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {

  public static final String DIR = "site/";

  static void generateWebsite() throws IOException {
    Files.createDirectories(Path.of(DIR));

    var comparisons = getPublishedComparisons();
    var categories = categories(comparisons);

    comparisons.forEach(comparison -> renderToFile(comparison, categories));
    categories.forEach(category -> renderToFile(category, categories));
    renderIndex(categories);
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
        .concat(Comparisons.getComparisons().stream(), Comparisons.getBookComparisons().stream())
        .filter(Comparison::published)
        .collect(Collectors.toList());
  }

  private static void renderToFile(Comparison comparison, List<Category> categories) {

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
