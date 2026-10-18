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
    return List.of(
        modernJava("Inline Short Files in Tests", groupNewLanguageFeatures,
            List.of("Text Blocks", "JUnit 5", "JSONAssert"),
            """
                This JUnit 5 test checks the JSON representation of a `Supply` with JSONAssert. The expected JSON lives in a separate file under `src/test/resources`, which a helper reads from the classpath.

                That's a common pattern, but it makes the test harder to read. The most important part, the expected output, is not visible in the test itself, it's one click away. When the test fails, you have to jump between two files to understand why. And over time, the resource folder fills up with files whose tests nobody remembers.
                """,
            """
                Since Java 15, text blocks let you write multi-line strings without escaping quotes or concatenating lines. So the expected JSON fits right into the test, formatted just like in the file before. Everything you need to understand the test is now in one place.

                The helper method and the resource file are gone. If you need to vary parts of the expected output, combine the text block with `formatted()`. Keep separate files for large inputs, though: inlining pays off for short snippets that you'd want to see at a glance.
                """),
        modernJava("Explain Unexpected Empty Optional", groupNewLanguageFeatures,
            List.of("Optional", "Error Handling"),
            """
                Since Java 10, `orElseThrow()` without a parameter returns the value of an `Optional` or throws a `NoSuchElementException`. Its documentation recommends it over `get()`, which does the same but whose name hides the fact that it may throw.

                But the exception says nothing more than *No value present*. When it shows up in the logs, you know that some supply wasn't found, but not which one. You have to reproduce the problem to find out.
                """,
            """
                The overload of `orElseThrow()` that takes a supplier, available since Java 8, lets you throw an exception with a meaningful message. Now the message says which ID wasn't found, so the log entry alone tells you what went wrong.

                The supplier is only called when the `Optional` is empty, so building the message costs nothing in the normal case. The same idea works with your own exception types, for example one that your web layer maps to a *404 Not Found*.
                """),
        modernJava("Switch to Switch Expressions", groupNewLanguageFeatures,
            List.of("Switch Expressions", "Java 14"),
            """
                This classic `switch` statement assigns a variable in every branch. That needs a declaration up front, a `break` after every case, and a `default` branch, even though every value of `Rank` is already covered, because otherwise the compiler complains that `result` might not be initialized.

                It's easy to get wrong. Forget one `break`, and the code silently falls through into the next case. And the `default` branch hides mistakes: when someone adds a new rank, it quietly gets one star instead of a compile error.
                """,
            """
                Since Java 14, `switch` can be an expression that returns a value directly. With the arrow syntax, each case maps to its result, there's no fall-through, so no `break`. The whole method becomes a single `return` statement, and the temporary variable is gone.

                Since every enum constant is covered, the compiler accepts the switch without a `default` branch. Even better, when someone adds a new rank later, this code stops compiling until the new case is handled. The compiler now checks what the `default` branch used to hide.
                """),
        modernJava("Model Data with Records", groupNewLanguageFeatures, List.of("Records", "Java 16"),
            """
                `Coordinates` is a plain data carrier with two fields, yet the class is more than 40 lines long. The constructor, the getters, `equals()`, `hashCode()`, and `toString()` are all boilerplate, the kind of code an IDE generates and nobody reads.

                It's also a maintenance trap. Every new field means touching five places, and forgetting one, say in `equals()`, is a silent bug. Meanwhile, the one interesting line, the range check of the latitude, is hard to spot among all the noise.
                """,
            """
                Since Java 16, a record declares its components once, in the header. The compiler generates the constructor, the accessors, `equals()`, `hashCode()`, and `toString()`. Records are immutable: all fields are `private final`, and there are no setters.

                The compact constructor keeps the validation, which is now the only code left to read. Note that the accessors are called `latitude()` instead of `getLatitude()`, so callers need a small change.

                Use records for values whose identity is their data, such as coordinates, amounts, or IDs. For entities with mutable state, a regular class remains the better choice.
                """),
        modernJava("Seal Your Hierarchies", groupNewLanguageFeatures, List.of("Sealed Classes", "Pattern Matching", "Java 21"),
            """
                `Cargo` is an open interface, so anyone can implement it, anywhere in the code base. The compiler can't know which kinds of cargo exist. That's why `weightInKg()` has to end with a `throw` for a case that nobody expects.

                When someone adds a new kind of cargo, say `Water`, everything still compiles. The missing branch only shows up as an exception at runtime, possibly in production, and only if a test happens to load water.
                """,
            """
                Since Java 17, a `sealed` interface lists all its implementations after `permits`. Now the compiler knows every kind of cargo, so a `switch` with type patterns, available since Java 21, can cover them all without a `default` branch. The `throw` is gone.

                The real benefit shows when the hierarchy grows: adding `Water` to the `permits` clause makes this switch fail to compile until it handles water. The compiler finds every place you need to change.

                Records are implicitly `final`, which is what a sealed hierarchy requires. Other permitted classes must be declared `final`, `sealed`, or `non-sealed`. Seal hierarchies that you own and switch over; keep interfaces open where others should plug in their own implementations.
                """),
        modernJava("Write Doc Comments in Markdown", groupNewLanguageFeatures, List.of("Javadoc", "Markdown", "Java 23"),
            """
                Classic Javadoc is HTML inside a `/** */` block. Tags like `<p>`, `<ul>`, `<li>`, `<em>`, and `<pre>{@code}` drown the actual text in markup.

                That's a pity, because you read doc comments in the editor far more often than in the generated documentation. And there, all this markup makes them hard to read and tedious to write.
                """,
            """
                Since Java 23 (JEP 467), doc comments that start with `///` are written in Markdown. A blank line replaces `<p>`, a `-` replaces `<ul><li>`, underscores replace `<em>`, backticks replace `{@code}`, and a fenced code block replaces `<pre>`.

                Block tags like `@param`, `@return`, and `@see`, as well as links to program elements, still work. The comment reads well in the source and renders just the same in the generated documentation.

                You don't have to convert everything at once: both styles can be mixed in the same file, so you can migrate one comment at a time.
                """));
  }

  private static Comparison modernJava(
      String name, String group, List<String> tags, String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText, "Modern Java");
  }
}
