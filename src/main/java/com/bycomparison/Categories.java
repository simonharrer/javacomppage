package com.bycomparison;

import java.util.List;

public class Categories {

  static List<Category> categories() {
    return List.of(
        new Category("Java", "Book at Pragmatic Programmers", List.of()),
        new Category("Java Concurrency", "Concurrency is hard. We're here to help!", List.of()),
        new Category("Modern Java",
            "Describes the new Java language features, starting from Java 9 and following",
            List.of()),
        new Category("Spring Boot",
            "A lot of Java developers don't write Java, they write Spring Boot.", List.of())
    );
  }
}
