package com.bycomparison;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class Main {

  public static void main(String[] args) throws Exception {
    var start = Instant.now();

    generate(new GeneratorConfiguration(Path.of("site"), true, false));
    generate(new GeneratorConfiguration(Path.of("site-private"), false, false));

    var end = Instant.now();
    var duration = Duration.between(start, end);
    System.out
        .printf("Everything OK. Generation took %s seconds.", duration.toSeconds());
  }

  private static void generate(GeneratorConfiguration configuration) throws IOException {
    System.out.format("Configuration: %s (%s, %s)%n", configuration.site(),
        configuration.published() ? "only published" : "all",
        configuration.preview() ? "previews" : "no previews");
    Generator.deleteWebsite(configuration);
    Generator.generateWebsite(configuration);
  }

}
