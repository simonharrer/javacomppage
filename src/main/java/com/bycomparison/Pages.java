package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Loads the hand-written pages from src/main/pages. A page file starts with a front matter comment
 * (one "key: value" per line), followed by an optional head block, followed by the body:
 *
 * <pre>
 * &lt;!--
 * title: The Story of Java by Comparison
 * description: How the book came to be.
 * canonical: /story
 * --&gt;
 * &lt;!-- head --&gt; ...JSON-LD... &lt;!-- /head --&gt;
 * &lt;section&gt;...&lt;/section&gt;
 * </pre>
 */
public class Pages {

  private static final Pattern FRONT_MATTER = Pattern.compile("\\A\\s*<!--\\R(.*?)\\R-->\\R?", Pattern.DOTALL);
  private static final Pattern HEAD = Pattern.compile("\\A\\s*<!-- head -->\\R(.*?)\\R<!-- /head -->\\R?", Pattern.DOTALL);

  static List<Page> load(Path directory) throws IOException {
    try (Stream<Path> files = Files.list(directory)) {
      return files
          .filter(path -> path.toString().endsWith(".html"))
          .sorted()
          .map(Pages::loadFile)
          .toList();
    }
  }

  static Page loadFile(Path file) {
    try {
      return parse(file.getFileName().toString(), Files.readString(file));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  static Page parse(String filename, String content) {
    var frontMatter = FRONT_MATTER.matcher(content);
    if (!frontMatter.find()) {
      throw new IllegalArgumentException(filename + " has no front matter");
    }
    Map<String, String> fields = new HashMap<>();
    frontMatter.group(1).lines().forEach(line -> {
      var colon = line.indexOf(':');
      if (colon > 0) {
        fields.put(line.substring(0, colon).trim(), line.substring(colon + 1).trim());
      }
    });
    var rest = content.substring(frontMatter.end());

    var head = "";
    var headBlock = HEAD.matcher(rest);
    if (headBlock.find()) {
      head = headBlock.group(1);
      rest = rest.substring(headBlock.end());
    }

    return new Page(
        filename,
        required(fields, "title", filename),
        required(fields, "description", filename),
        required(fields, "canonical", filename),
        fields.getOrDefault("og_type", ""),
        fields.getOrDefault("og_title", ""),
        fields.getOrDefault("og_description", ""),
        fields.getOrDefault("og_image", ""),
        fields.getOrDefault("robots", ""),
        head,
        rest,
        "");
  }

  private static String required(Map<String, String> fields, String key, String filename) {
    var value = fields.get(key);
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(filename + " lacks '" + key + "' in its front matter");
    }
    return value;
  }
}
