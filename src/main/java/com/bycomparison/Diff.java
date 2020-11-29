package com.bycomparison;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Patch;
import java.util.stream.Collectors;

public class Diff {

  public static String of(Comparison comparison) {
    Patch<String> patch;
    try {
      patch = DiffUtils.diff(comparison.beforeCode().lines().collect(Collectors.toList()),
          comparison.afterCode().lines().collect(
              Collectors.toList()));
    } catch (DiffException e) {
      throw new RuntimeException(e);
    }
    return UnifiedDiffUtils
        .generateUnifiedDiff("BEFORE", "AFTER", comparison.beforeCode().lines().collect(
            Collectors.toList()), patch, 100).stream().collect(Collectors.joining("\n"));
  }

  public static String withoutPreabmle(Comparison comparison) {
    Patch<String> patch;
    try {
      patch = DiffUtils.diff(comparison.beforeCode().lines().collect(Collectors.toList()),
          comparison.afterCode().lines().collect(
              Collectors.toList()));
    } catch (DiffException e) {
      throw new RuntimeException(e);
    }
    return UnifiedDiffUtils
        .generateUnifiedDiff("BEFORE", "AFTER", comparison.beforeCode().lines().collect(
            Collectors.toList()), patch, 100).stream().skip(3).collect(Collectors.joining("\n"));
  }
}
