package com.bycomparison;

import java.util.List;

public class ComparisonsModernJava {

  /*
  https://github.com/simonharrer/javacomp/issues/3
  default implementation in interfaces
   */

  public static List<Comparison> getComparisons() {
    var groupTesting = "Chapter 2: Modern Testing";
    return List.of(
        modernJava("Group Your Tests", groupTesting, List.of("JUnit5"), "", ""),
        modernJava("Nest Your Tests", groupTesting, List.of("JUnit5"), "", ""),
        Comparison.empty("Use assertThat", groupTesting, "Modern Java"),
        Comparison.empty("Irgendwas mit mocking", groupTesting, "Modern Java"),
        modernJava(
            "Remove Unnecessary Interface",
            "Design",
            List.of("Implementation", "Interface"),
            """
                * one interface and one implementation
                * either interface or implementation has bad name `ICalculator` or `CalculatorImpl` or `SimpleCalculator`
                """,
            """
                * no naming issue by inlining interface
                """),
        Comparison.empty("Avoid OutOfMemoryErrors", "Know the Gotchas", "Modern Java"),
        Comparison.empty("Avoid Stackoverflows", "Know the Gotchas", "Modern Java"),
        Comparison.empty("Avoid Linebreaks Issues", "Know the Gotchas", "Modern Java"),
        Comparison.empty("Avoid Encoding Issues", "Know the Gotchas", "Modern Java"),
        Comparison.empty("Avoid Locale Issues", "Know the Gotchas", "Modern Java"));
  }

  public static List<Comparison> chapter1NewLanguageFeatures() {
    var groupNewLanguageFeatures = "Chapter 1: New Language Features";
    var chapter1NewLanguageFeatures = List.of(modernJava(
        "Inline Short Files in Tests",
        groupNewLanguageFeatures,
        List.of("Text Blocks", "JUnit 5", "JSONAssert"),
        """
            * uses JSONAssert
            * is a JUnit 5 unit test
            * read file from classpath (part of `src/test/resources`)
            * expect output not directly visible, one click away""",
        """
            * expected output directly visible
            * makes use of text block
            * templating easy with `formatted` ???
            """),
        modernJava(
            "Explain Unexpected Empty Optional",
            groupNewLanguageFeatures,
            List.of("Optional", "Error Handling"),
            """
                * since Java 10 there's orElseThrows() without a parameter
                * according to docs preferred over get() (which basically does the same)
                * exception doesn't say which id wasn't found""",
            """
                * exception now says which id wasn't found
                * works with Optional out of the box (since Java 8)
                """),
        modernJava("Switch to Switch Expressions", groupNewLanguageFeatures, List.of("Modern Java"),
            """
                * old switch statement
                * default case necessary despite every enum type covered
                """,
            """
                * use switch expression
                * one single return statement
                * auto-break
                * default case no longer necessary as compiler knows that every enum type is covered
                """), Comparison.empty("Immutable Records", "Modern Java"));
    return chapter1NewLanguageFeatures;
  }

  private static Comparison modernJava(
      String name, String group, List<String> tags, String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText, "Modern Java");
  }
}
