package com.bycomparison;

import com.fizzed.rocker.RockerModel;
import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.algorithm.DiffException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Main {

  public static final String DIR = "site/";

  public static void main(String[] args) throws IOException, DiffException {
    Files.createDirectories(Path.of(DIR));
    final var list = Comparisons.getComparisons();
    list.forEach(Main::renderToFile);
    list.stream().collect(Collectors.groupingBy(Comparison::category))
        .forEach((category, comparisons) -> renderToFile(new Category(category, comparisons)));
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
