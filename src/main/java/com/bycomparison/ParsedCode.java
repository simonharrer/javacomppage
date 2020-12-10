package com.bycomparison;

import java.util.List;
import java.util.stream.Collectors;

public record ParsedCode(String code, List<Integer> highlightedLines) {

  public String highlight() {
    return highlightedLines().stream().map(Object::toString)
        .collect(
            Collectors.joining(","));
  }

}
