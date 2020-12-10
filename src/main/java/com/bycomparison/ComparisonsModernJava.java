package com.bycomparison;

import java.util.List;

public class ComparisonsModernJava {

  /*
  https://github.com/simonharrer/javacomp/issues/3
   */

  public static List<Comparison> getComparisons() {
    return List
        .of(modernJava("Inline Short Files in Tests", "Java 15",
            List.of("Text Blocks", "Testing", "JUnit 5", "JSONAssert"), """
                * uses JSONAssert
                * is a JUnit 5 unit test
                * read file from classpath (part of `src/test/resources`)
                * expect output not directly visible, one click away""", """
                * expected output directly visible
                * makes use of text block
                * templating easy with `formatted` ???
                """),
            modernJava("Explain Unexpected Empty Optional", "Java 10",
                List.of("Optional", "Error Handling"), """
                    * since Java 10 there's orElseThrows() without a parameter
                    * according to docs preferred over get() (which basically does the same)
                    * exception doesn't say which id wasn't found""", """
                    * exception now says which id wasn't found
                    * works with Optional out of the box (since Java 8)
                    """),
            modernJava("Remove Unnecessary Interface", "Design",
                List.of("Implementation", "Interface"), """
                    * one interface and one implementation
                    * either interface or implementation has bad name `ICalculator` or `CalculatorImpl` or `SimpleCalculator`
                    """, """
                    * no naming issue by inlining interface
                    """),
            Comparison.empty("Keep Related Tests Together", "Modern Java"),
            Comparison.empty("Use assertThat", "Modern Java"),
            Comparison.empty("Document Your Modules", "Java 9", "Modern Java"),
            Comparison.empty("Irgendwas mit mocking", "Modern Java"),
            Comparison.empty("Avoid OutOfMemoryErrors", "Know the Gotchas", "Modern Java"),
            Comparison.empty("Avoid Stackoverflows", "Know the Gotchas", "Modern Java"),
            Comparison.empty("Avoid Linebreaks Issues", "Know the Gotchas", "Modern Java"),
            Comparison.empty("Avoid Encoding Issues", "Know the Gotchas", "Modern Java"),
            Comparison.empty("Avoid Locale Issues", "Know the Gotchas", "Modern Java")
        )
        ;
  }

  private static Comparison modernJava(String name, String group, List<String> tags,
      String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText,
        "Modern Java");
  }

}
