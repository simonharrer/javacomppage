package com.bycomparison;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Builds the website.
 *
 * <p>Usage: {@code Main [output-directory] [--drafts] [--previews]}. The output directory defaults to
 * target/site. With --drafts, the unpublished comparisons are rendered into a separate
 * target/site-drafts directory as well.
 */
public class Main {

  public static void main(String[] args) throws Exception {
    var start = Instant.now();
    var arguments = List.of(args);
    var output = arguments.stream().filter(a -> !a.startsWith("--")).findFirst().orElse("target/site");
    var previews = arguments.contains("--previews");

    generate(new GeneratorConfiguration(Path.of(output), Path.of("static"), Path.of("src/main/pages"), true, previews));
    if (arguments.contains("--drafts")) {
      generate(new GeneratorConfiguration(Path.of("target/site-drafts"), Path.of("static"), Path.of("src/main/pages"), false, previews));
    }

    var duration = Duration.between(start, Instant.now());
    System.out.printf("Everything OK. Generation took %s seconds.%n", duration.toSeconds());
  }

  private static void generate(GeneratorConfiguration configuration) throws IOException {
    System.out.format("Configuration: %s (%s, %s)%n", configuration.output(),
        configuration.published() ? "only published" : "all",
        configuration.preview() ? "previews" : "no previews");
    Generator.generateWebsite(configuration);
  }

}
