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
                """),
        modernJava("Model Data with Records", groupNewLanguageFeatures, List.of("Records", "Java 16"),
            """
                * a plain data carrier with two fields, but more than 40 lines long
                * constructor, getters, `equals()`, `hashCode()`, and `toString()` are all boilerplate
                * every new field means touching five places, and forgetting one (say, in `equals()`) is a silent bug
                * the one interesting line, the range check, is hard to spot
                """,
            """
                * a record declares the components once, the compiler generates the constructor, accessors, `equals()`, `hashCode()`, and `toString()`
                * records are immutable: all fields are `private final`, there are no setters
                * the compact constructor keeps the validation, now the only code left to read
                * accessors are called `latitude()` instead of `getLatitude()`, so callers need a small change
                * use records for values whose identity is their data, not for entities with mutable state
                """),
        modernJava("Write Doc Comments in Markdown", groupNewLanguageFeatures, List.of("Javadoc", "Markdown", "Java 23"),
            """
                * Javadoc is HTML inside a `/** */` block
                * tags like `<p>`, `<ul>`, `<li>`, `<em>`, and `<pre>{@code}` drown the actual text
                * hard to read in the editor, where you look at it most often
                """,
            """
                * since Java 23 (JEP 467), doc comments starting with `///` are written in Markdown
                * blank lines instead of `<p>`, `-` instead of `<ul><li>`, `_` instead of `<em>`, backticks instead of `{@code}`, fenced code blocks instead of `<pre>`
                * block tags like `@param`, `@return`, and `@see` and links to program elements still work
                * the comment reads well in the source and renders the same in the generated documentation
                * you can migrate one comment at a time, both styles can be mixed in the same file
                """));
    return chapter1NewLanguageFeatures;
  }

  private static Comparison modernJava(
      String name, String group, List<String> tags, String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText, "Modern Java");
  }
}
