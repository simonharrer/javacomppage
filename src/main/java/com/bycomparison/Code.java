package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Code {

  static String parseBeforeCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/before/Before.java";
    return parseCodePart(filename, "// BEFORE");
  }

  static String parseAfterCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/after/After.java";
    return parseCodePart(filename, "// AFTER");
  }

  private static String parseCodePart(String filename, String delimiter) {
    try {
      var lines = Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8);
      var result = new ArrayList<String>();
      boolean active = false;
      for (var line : lines) {
        if (line.startsWith(delimiter + ":end")) {
          active = false;
        }

        if (active) {
          result.add(line);
        }

        if (line.startsWith(delimiter + ":start")) {
          active = true;
        }
      }
      return String.join("\n", result);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
