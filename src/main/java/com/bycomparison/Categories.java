package com.bycomparison;

import java.util.List;

public class Categories {

  static List<Category> categories() {
    return List.of(
        new Category("Java", """
            For over six years, we've taught Java at University and reviewed thousands of lines of Java code written by young students. In our code reviews, we've covered in detail why some code is bad, how to write it in a better way and why the better way is really better. Over time, we've realized that we told every new generation of students basically the same things. And from those student reviews, we've published our book [Java by Comparison](https://java.by-comparison.com). Please note, however, that although we've put every comparison from the book below like a table of contents, the actual contents are only available in the actual book.
            """, List.of()),
        new Category("Java Concurrency", "Concurrency is hard, especially protecting shared memory. In the comparisons below, we show an evolution of making unprotected and undocumented data structures (a counter and a collection) safe to use in a concurrent environment. The world of concurrency certainly encompasses much more, but in practice, protecting the shared data structures typically suffices.", List.of()),
        new Category("Modern Java",
            "Describes the new Java language features, starting from Java 9 and following",
            List.of()),
        new Category("Spring Boot",
            "A lot of Java developers don't write Java, they write Spring Boot.", List.of())
    );
  }

}
