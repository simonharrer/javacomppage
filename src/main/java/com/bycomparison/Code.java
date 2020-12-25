package com.bycomparison;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Code {

  static ParsedCode parseBeforeCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/before/Before.java";
    return parse(filename, "BEFORE");
  }

  static ParsedCode parseAfterCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/after/After.java";
    return parse(filename, "AFTER");
  }

  private static ParsedCode parse(String filename, String delimiter) {
    try {
      var path = Path.of(filename);

      if (!Files.exists(path)) {
        System.out.println("ERROR: path " + path + " doesn't exist");
        return new ParsedCode("", List.of());
      }

      return p(delimiter, Files.readAllLines(path, StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static ParsedCode p(String delimiter, List<String> lines) {
    var highlightedLines = new ArrayList<Integer>();
    var result = new ArrayList<String>();
    boolean highlight = false;
    boolean active = false;
    int ignoredLineNumbers = 0;
    int lineNumberModifier = 0;
    for (int lineNumber = 1, linesSize = lines.size() + 1; lineNumber < linesSize; lineNumber++) {
      String line = lines.get(lineNumber - 1);
      if (line.contains(delimiter + ":end")) {
        active = false;
        ignoredLineNumbers++;
      }
      if (active && !(line.contains("END_HIGHLIGHT") || line.contains("START_HIGHLIGHT"))) {
        result.add(line);
      }
      if (line.contains(delimiter + ":start")) {
        active = true;
        ignoredLineNumbers++;
        lineNumberModifier = lineNumber;
      }

      if (line.contains("END_HIGHLIGHT")) {
        highlight = false;
        ignoredLineNumbers++;
      }
      if (highlight) {
        highlightedLines.add(lineNumber - ignoredLineNumbers - lineNumberModifier + 1);
      }
      if (line.contains("START_HIGHLIGHT")) {
        highlight = true;
        ignoredLineNumbers++;
      }
    }
    return new ParsedCode(String.join("\n", result), highlightedLines);
  }

  public static ParsedCode parseBeforeCode(String code) {
    return p("BEFORE", code.lines().collect(Collectors.toList()));
  }

  public static ParsedCode parseAfterCode(String code) {
    return p("AFTER", code.lines().collect(Collectors.toList()));
  }
}
