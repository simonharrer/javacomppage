package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ComparisonsJavaConcurrency {

  public static List<Comparison> getComparisons() {
    return List
            .of(
                    new Comparison("Document for Concurrency", "Documentation", "Java Concurrency",
                            List.of(),
                            parseBeforeCodePart("c2"), "", "", parseAfterCodePart("c2"), "1,4", "",
                            "java", "", true, false, ""),
                    new Comparison("Guard your Waits", "Locking", "Java Concurrency",
                            List.of(),
                            parseBeforeCodePart("c4"), "5",
                            Markdown.toHtml("""
                                    * Another Thread can modify `isReadyToLaunch`
                                    * Countdown is only initiated when isReadyToLaunch becomes true
                                    * Inefficient, since boolean is constantly checked"""),
                            parseAfterCodePart("c4"), "4,5,7", Markdown.toHtml("""
                            * `wait()` allows the Thread to suspend execution until it is woken up by a `notify()`.
                            * `synchronized` is necessary to prevent putting this Thread to `sleep` multiple times.
                            * `wait()` can be interrupted, but the value of `isReadyToLaunch` determines whether the `while` loop exits. Therefore, the `InterruptedException` can be ignored!                         
                            """),
                            "java", "", true, false, "")
            );
  }

  private static String parseBeforeCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/before/Before.java";
    return parseCodePart(filename, "// BEFORE");
  }

  private static String parseAfterCodePart(String id) {
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
