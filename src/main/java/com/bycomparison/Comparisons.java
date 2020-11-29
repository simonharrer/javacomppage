package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Comparisons {

  public static List<Comparison> getBookComparisons() {
    List<Comparison> result = new ArrayList<>();
    var chapter1 = "Start Cleaning Up";
    result.add(bookComparison("Avoid Unnecessary Comparisons", chapter1, 3, ""));
    result.add(bookComparison("Avoid Negations", chapter1, 5, ""));
    result.add(bookComparison("Return Boolean Expressions Directly", chapter1, 7, "http://media.pragprog.com/titles/javacomp/boolean.pdf"));
    result.add(bookComparison("Simplify Boolean Expressions", chapter1, 9, ""));
    result.add(bookComparison("Avoid NullPointerException in Conditional", chapter1, 11, ""));
    result.add(bookComparison("Avoid Switch Fallthrough", chapter1, 13, ""));
    result.add(bookComparison("Always Use Braces", chapter1, 15, ""));
    result.add(bookComparison("Ensure Code Symmetry", chapter1, 17, ""));

    var chapter2 = "Level Up Your Code Style";
    result.add(bookComparison("Replace Magic Numbers with Constants", chapter2, 20, ""));
    result.add(bookComparison("Favor Enums Over Integer Constants", chapter2, 22, ""));
    result.add(bookComparison("Favor For-Each Over For Loops", chapter2, 24, ""));
    result.add(bookComparison("Avoid Collection Modification During Iteration", chapter2, 26, ""));
    result
        .add(bookComparison("Avoid Compute-Intense Operations During Iteration", chapter2, 28, ""));
    result.add(bookComparison("Group with New Lines", chapter2, 30, ""));
    result.add(bookComparison("Favor Format Over Concatenation", chapter2, 32, ""));
    result.add(bookComparison("Favor Java API Over DIY", chapter2, 34, ""));
    return result;
  }

  private static Comparison bookComparison(String name, String chapter, int page,
      String sampleChapterDownloadLink) {
    return new Comparison(name, "Java",
        List.of(chapter),
        "", "", "" + page, "", "", "" + (page + 1), "java", "book", true, true,
        sampleChapterDownloadLink);
  }

  public static List<Comparison> getComparisons() {
    return List
        .of(new Comparison("Replace Small Test Files with Text Blocks", "Java",
                List.of("Java 15"),
                "", "", "", "", "", "", "java", "", true, false, ""),
            new Comparison("Safe the Planet", "Spring Boot",
                List.of("Spring"),
                "spring.main.banner-mode=console", "1", "", "spring.main.banner-mode=off", "1", "",
                "properties", "", true, false, ""),
            new Comparison("Document for Concurrency", "Java Concurrency",
                List.of("Java", "Concurrency"),
                parseBeforeCodePart("c2"), "", "", parseAfterCodePart("c2"), "1,4", "",
                "java", "", true, false, ""),
            new Comparison("Favor Constructor over Field Injection", "Spring Boot",
                List.of("Spring"),
                parseBeforeCodePart("c1"), "4",
                Markdown.toHtml("""
                    Have a look at the code above. 
                    We have a SpaceSuit component with a single dependency, the `OxygenMonitor`.
                    Upon start, Spring will create one instance of that class for us, providing it an instance of the OxygenMonitor, and inject the SpaceSuit instance where appropriate in other components.

                    The field where Spring should inject the OxygenMonitor dependency is marked with @Autowired and is private.
                    Because private really means private in Java, Spring needs to change the accessibility of that field at runtime so it can set its value. 
                    Depending on your security manager, this may fail, consequently preventing your app to start up in that case. 
                    Changing the field visibility to default would solve this, but would allow write access to that field, which might be even worse.

                    A field that is not marked final can be reassigned to another value anytime. 
                    So after Spring has injected the OxygenMonitor, a method within the SpaceSuit class might as well change that field, which is probably something you would not want either. 
                    Setting the field to final prevents reassigning the value, but this is no real solution here. 
                    Because it prevents Spring to inject the value in the field.

                    - Not final, can be changed within any methods later on
                    - No knowledge when the injection happens
                    - Field could be hidden somewhere in the class (although this is bad practice)"""),
                parseAfterCodePart("c1"), "4-8", Markdown.toHtml("""
                The code is clearer now. 
                The field is set through the constructor upon instance creation. 
                And because we marked the field final it won't be able to change afterward anymore. 
                We also get a bonus: the @Autowired annotation is optional for the constructor and can be left out.
                                
                With field injection, you don't feel the cost of adding another dependency. 
                It's just another line added to the source code. 
                It's different with constructor injection because you need to add the dependency to the constructor. 
                The constructor becomes an overview of all dependencies of that class, and the more
                dependencies, the larger the constructor with a lot of parameters. 
                We developers are trained to keep
                parameters in methods to a minimum, and this intuition will kick in for constructors, too, helping
                you to spot classes with too many dependencies more quickly.
                    
                There is, however, an exception to this rule. 
                JUnit requires a no-args constructor for every test class.
                So we can't use constructor injection in tests and have to fall back to field injection.
                                
                - TODO thymeleaf and MVC
                - TODO web vs. webflux
                """), "java", "", true, false, ""));
  }

  private static String parseBeforeCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/before/Before.java";
    return parseCodePart(filename, "// BEFORE");
  }

  private static String parseAfterCodePart(String id) {
    var filename = "src/main/java/com/bycomparison/" + id + "/after/After.java";
    return parseCodePart(filename, "// AFTER");
  }

  private static String parseCodePart(String filename, String delimiter) {
    try {
      var lines = Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8);
      var result = new ArrayList<String>();
      boolean active = false;
      for (var line : lines) {
        if (line.startsWith(delimiter + ":end")) {
          active = false;
        }

        if (active) {
          result.add(line);
        }

        if (line.startsWith(delimiter + ":start")) {
          active = true;
        }
      }
      return String.join("\n", result);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
