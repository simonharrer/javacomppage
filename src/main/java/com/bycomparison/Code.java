package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Code {

  static ParsedCode parseBeforeCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/before/Before.java";
    return parse(filename, "// BEFORE");
  }

  static ParsedCode parseAfterCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/after/After.java";
    return parse(filename, "// AFTER");
  }

  private static ParsedCode parse(String filename, String delimiter) {
    try {
      var lines = Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8);
      var highlightedLines = new ArrayList<Integer>();
      var result = new ArrayList<String>();
      boolean highlight = false;
      boolean active = false;
      int ignoredLineNumbers = 0;
      int lineNumberModifier = 0;
      for (int lineNumber = 1, linesSize = lines.size() + 1; lineNumber < linesSize; lineNumber++) {
        String line = lines.get(lineNumber - 1);
        if (line.trim().startsWith(delimiter + ":end")) {
          active = false;
          ignoredLineNumbers++;
        }
        if (active && !(line.trim().startsWith("// END_HIGHLIGHT") || line.trim().startsWith("// START_HIGHLIGHT"))) {
          result.add(line);
        }
        if (line.trim().startsWith(delimiter + ":start")) {
          active = true;
          ignoredLineNumbers++;
          lineNumberModifier = lineNumber;
        }

        if(line.trim().startsWith("// END_HIGHLIGHT")) {
          highlight = false;
          ignoredLineNumbers++;
        }
        if (highlight) {
          highlightedLines.add(lineNumber - ignoredLineNumbers - lineNumberModifier + 1);
        }
        if (line.trim().startsWith("// START_HIGHLIGHT")) {
          highlight = true;
          ignoredLineNumbers++;
        }
      }
      var r = new ParsedCode(String.join("\n", result), highlightedLines);
      return r;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
