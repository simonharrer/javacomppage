package com.bycomparison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComparisonsJava {

  public static List<Comparison> getComparisons() {
    List<Comparison> result = new ArrayList<>();
    var chapter1 = "Chapter 1: Start Cleaning Up";
    result.add(bookComparison("Avoid Unnecessary Comparisons", chapter1, 3, ""));
    result.add(bookComparison("Avoid Negations", chapter1, 5, ""));
    result.add(bookComparison("Return Boolean Expressions Directly", chapter1, 7,
        "https://media.pragprog.com/titles/javacomp/boolean.pdf"));
    result.add(bookComparison("Simplify Boolean Expressions", chapter1, 9, ""));
    result.add(bookComparison("Avoid NullPointerException in Conditional", chapter1, 11, ""));
    result.add(bookComparison("Avoid Switch Fallthrough", chapter1, 13, ""));
    result.add(bookComparison("Always Use Braces", chapter1, 15, ""));
    result.add(bookComparison("Ensure Code Symmetry", chapter1, 17, ""));

    var chapter2 = "Chapter 2: Level Up Your Code Style";
    result.add(bookComparison("Replace Magic Numbers with Constants", chapter2, 20, ""));
    result.add(bookComparison("Favor Enums Over Integer Constants", chapter2, 22, ""));
    result.add(bookComparison("Favor For-Each Over For Loops", chapter2, 24, ""));
    result.add(bookComparison("Avoid Collection Modification During Iteration", chapter2, 26, ""));
    result
        .add(bookComparison("Avoid Compute-Intense Operations During Iteration", chapter2, 28, ""));
    result.add(bookComparison("Group with New Lines", chapter2, 30, ""));
    result.add(bookComparison("Favor Format Over Concatenation", chapter2, 32, ""));
    result.add(bookComparison("Favor Java API Over DIY", chapter2, 34, ""));

    var chapter3 = "Chapter 3: Use Comments Wisely";
    result.add(bookComparison("Remove Superfluous Comments", chapter3, 38, ""));
    result.add(bookComparison("Remove Commented-Out Code", chapter3, 40, ""));
    result.add(bookComparison("Replace Comments with Constants", chapter3, 42, ""));
    result.add(bookComparison("Replace Comments with Utility Methods", chapter3, 44, ""));
    result
        .add(bookComparison("Document Implementation Decisions", chapter3, 46, ""));
    result.add(bookComparison("Document Using Examples", chapter3, 48,
        "https://media.pragprog.com/titles/javacomp/examples.pdf"));
    result.add(bookComparison("Structure JavaDoc of Packages", chapter3, 50, ""));
    result.add(bookComparison("Structure JavaDoc of Classes and Interfaces", chapter3, 52, ""));
    result.add(bookComparison("Structure JavaDoc of Methods", chapter3, 54, ""));
    result.add(bookComparison("Structure JavaDoc of Constructors", chapter3, 56, ""));

    var chapter4 = "Chapter 4: Name Things Right";
    result.add(bookComparison("Use Java Naming Conventions", chapter4, 60, ""));
    result.add(bookComparison("Follow Getter/Setter Conventions for Frameworks", chapter4, 62, ""));
    result.add(bookComparison("Avoid Single-Letter Names", chapter4, 64, ""));
    result.add(bookComparison("Avoid Abbreviations", chapter4, 66, ""));
    result
        .add(bookComparison("Avoid Meaningless Terms", chapter4, 68, ""));
    result.add(bookComparison("Use Domain Terminology", chapter4, 70, ""));

    var chapter5 = "Chapter 5: Prepare for Things Going Wrong";
    result.add(bookComparison("Fail Fast", chapter5, 74, ""));
    result.add(bookComparison("Always Catch Most Specific Exception", chapter5, 76, ""));
    result.add(bookComparison("Explain Cause in Message", chapter5, 78, ""));
    result.add(bookComparison("Avoid Breaking the Cause Chain", chapter5, 80, ""));
    result
        .add(bookComparison("Expose Cause in Variable", chapter5, 82, ""));
    result.add(bookComparison("Always Check Type Before Cast", chapter5, 84, ""));
    result.add(bookComparison("Always Close Resources", chapter5, 86, ""));
    result.add(bookComparison("Always Close Multiple Resources", chapter5, 88, ""));
    result.add(bookComparison("Explain Empty Catch", chapter5, 90, ""));

    var chapter6 = "Chapter 6: Assert Things Going Right";
    result.add(bookComparison("Structure Tests Into Given-When-Then", chapter6, 94, ""));
    result.add(bookComparison("Use Meaningful Assertions", chapter6, 96, ""));
    result.add(bookComparison("Expected Before Actual Value", chapter6, 98, ""));
    result.add(bookComparison("Use Reasonable Tolerance Values", chapter6, 100, ""));
    result
        .add(bookComparison("Let JUnit Handle Exceptions", chapter6, 102, ""));
    result.add(bookComparison("Describe Your Tests", chapter6, 104, ""));
    result.add(bookComparison("Favor Standalone Tests", chapter6, 106, ""));
    result.add(bookComparison("Parametrize Your Tests", chapter6, 108, ""));
    result.add(bookComparison("Cover the Edge Cases", chapter6, 110, ""));

    var chapter7 = "Chapter 7: Design Your Objects";
    result.add(bookComparison("Split Method with Boolean Parameters", chapter7, 114,
        "https://media.pragprog.com/titles/javacomp/split.pdf"));
    result.add(bookComparison("Split Method with Optional Parameters", chapter7, 116, ""));
    result.add(bookComparison("Favor Abstract Over Concrete Types", chapter7, 118, ""));
    result.add(bookComparison("Favor Immutable Over Mutable State", chapter7, 120, ""));
    result
        .add(bookComparison("Combine State and Behavior", chapter7, 122, ""));
    result.add(bookComparison("Avoid Leaking References", chapter7, 124, ""));
    result.add(bookComparison("Avoid Returning Null", chapter7, 126, ""));

    var chapter8 = "Chapter 8: Let Your Data Flow";
    result.add(bookComparison("Favor Lambdas Over Anonymous Classes", chapter8, 130, ""));
    result.add(bookComparison("Favor Functional Over Imperative Style", chapter8, 132, ""));
    result.add(bookComparison("Favor Method References Over Lambdas", chapter8, 134, ""));
    result.add(bookComparison("Avoid Side Effects", chapter8, 136, ""));
    result
        .add(bookComparison("Use Collect for Terminating Complex Streams", chapter8, 138, ""));
    result.add(bookComparison("Avoid Exceptions in Streams", chapter8, 140, ""));
    result.add(bookComparison("Favor Optional Over Null", chapter8, 142, ""));
    result.add(bookComparison("Avoid Optional Fields or Parameters", chapter8, 144, ""));
    result.add(bookComparison("Use Optionals as Streams", chapter8, 146, ""));

    return result;
  }

  private static Comparison bookComparison(String name, String chapter, int page,
      String sampleChapterDownloadLink, String... tags) {
    return new Comparison(name, chapter, "Java",
        Arrays.asList(tags),
        "", "", "" + page, "", "", "" + (page + 1), "java", "", true, true,
        sampleChapterDownloadLink);
  }
}
