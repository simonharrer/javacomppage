package com.bycomparison;

import java.util.List;

public record ParsedCode(String code, List<Integer> highlightedLines) {

}
