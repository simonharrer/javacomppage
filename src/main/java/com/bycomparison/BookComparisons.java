package com.bycomparison;

import java.util.ArrayList;
import java.util.List;

public class BookComparisons {

  public static List<Comparison> getBookComparisons() {
    List<Comparison> result = new ArrayList<>();
    var chapter1 = "Start Cleaning Up";
    result.add(bookComparison("Avoid Unnecessary Comparisons", chapter1, 3, ""));
    result.add(bookComparison("Avoid Negations", chapter1, 5, ""));
    result.add(bookComparison("Return Boolean Expressions Directly", chapter1, 7,
        "http://media.pragprog.com/titles/javacomp/boolean.pdf"));
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

    var chapter3 = "Use Comments Wisely";
    result.add(bookComparison("Remove Superfluous Comments", chapter3, 38, ""));
    result.add(bookComparison("Remove Commented-Out Code", chapter3, 40, ""));
    result.add(bookComparison("Replace Comments with Constants", chapter3, 42, ""));
    result.add(bookComparison("Replace Comments with Utility Methods", chapter3, 44, ""));
    result
        .add(bookComparison("Document Implementation Decisions", chapter3, 46, ""));
    result.add(bookComparison("Document Using Examples", chapter3, 48, "http://media.pragprog.com/titles/javacomp/examples.pdf"));
    result.add(bookComparison("Structure JavaDoc of Packages", chapter3, 50, ""));
    result.add(bookComparison("Structure JavaDoc of Classes and Interfaces", chapter3, 52, ""));
    result.add(bookComparison("Structure JavaDoc of Methods", chapter3, 54, ""));
    result.add(bookComparison("Structure JavaDoc of Constructors", chapter3, 56, ""));

    var chapter4 = "Name Things Right";
    result.add(bookComparison("Use Java Naming Conventions", chapter4, 60, ""));
    result.add(bookComparison("Follow Getter/Setter Conventions for Frameworks", chapter4, 62, ""));
    result.add(bookComparison("Avoid Single-Letter Names", chapter4, 64, ""));
    result.add(bookComparison("Avoid Abbreviations", chapter4, 66, ""));
    result
        .add(bookComparison("Avoid Meaningless Terms", chapter4, 68, ""));
    result.add(bookComparison("Use Domain Terminology", chapter4, 70, ""));

    var chapter5 = "Prepare for Things Going Wrong";
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

    return result;
  }

  private static Comparison bookComparison(String name, String chapter, int page,
      String sampleChapterDownloadLink) {
    return new Comparison(name, "Java",
        List.of(chapter),
        "", "", "" + page, "", "", "" + (page + 1), "java", "book", true, true,
        sampleChapterDownloadLink);
  }
}
