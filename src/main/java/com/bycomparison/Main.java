package com.bycomparison;

import com.fizzed.rocker.RockerModel;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static final String DIR = "site/";

  public static void main(String[] args) throws Exception {
    Files.createDirectories(Path.of(DIR));

    var comparisons = getPublishedComparisons();
    var categories = categories(comparisons);

    comparisons.forEach(Main::renderToFile);
    categories.forEach(Main::renderToFile);
  }

  private static List<Category> categories(List<Comparison> publishedComparisons) {
    return publishedComparisons.stream().collect(Collectors.groupingBy(Comparison::category))
        .entrySet().stream().map(e -> new Category(e.getKey(), e.getValue()))
        .collect(Collectors.toList());
  }

  private static List<Comparison> getPublishedComparisons() {
    return Comparisons.getComparisons().stream().filter(Comparison::published)
        .collect(Collectors.toList());
  }

  private static void renderToFile(Comparison comparison) {

    renderToFile(comparison.filename(), com.bycomparison.views.comparison.template(comparison));
  }

  private static void renderToFile(Category category) {
    renderToFile(category.filename(), com.bycomparison.views.category.template(category));
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
