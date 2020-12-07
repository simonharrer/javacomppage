package com.bycomparison;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class Main {

  public static void main(String[] args) throws Exception {
    var start = Instant.now();

    var site = Path.of("site");
    Generator.deleteWebsite(site);
    Generator.generateWebsite(site);

    var end = Instant.now();
    var duration = Duration.between(start, end);
    System.out
        .printf("Everything OK. Generation took %s seconds.", duration.toSeconds());
  }

}
