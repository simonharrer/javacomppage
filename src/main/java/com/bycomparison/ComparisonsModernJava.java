package com.bycomparison;

import java.util.List;

public class ComparisonsModernJava {

  public static List<Comparison> getComparisons() {
    return List
        .of(new Comparison("Replace Small Test Files with Text Blocks", "Java 15", "Modern Java",
            List.of("Text Blocks", "Testing"),
            "", "", "", "", "", "", "java", "", true, false, ""));
  }

}
