package com.bycomparison;

import java.util.List;

public class Categories {

  static List<Category> categories() {
    return List.of(
        new Category("Java", """
            For over six years, we've taught Java at University and reviewed thousands of lines of Java code written by young students. In our code reviews, we've covered in detail why some code is bad, how to write it in a better way and why the better way is really better. Over time, we've realized that we told every new generation of students basically the same things. And from those student reviews, we've published our book [Java by Comparison](https://java.by-comparison.com). 
            
            *Please note, however, that although we've put every comparison from the book below like a table of contents, the actual contents are only available in the actual book.*
            """, List.of()),
        new Category("Java Concurrency", """
            Concurrency is hard, especially protecting shared memory. As soon as two threads read and write the same data, code that looks perfectly fine can lose updates, see stale values, or deadlock, and these bugs rarely show up in a quick test run.

            In the comparisons below, we take two everyday data structures, a counter and a collection, that are neither protected nor documented, and make them safe to use in a concurrent environment, one small step at a time. Each step shows the code before and after, and explains what the change protects against and what it costs:

            * **Chapter 1** starts with `synchronized`, documents the thread-safety guarantees, and moves on to explicit, well-scoped, hidden, and fair locks. It ends with `AtomicLong`, which avoids locks altogether, and `LongAdder`, which avoids the busy retrying of compare-and-set when many threads count at once.
            * **Chapter 2** does the same for collections: from guarding a plain `ArrayList` yourself, to synchronized wrappers, to the concurrent collections of `java.util.concurrent`.

            The world of concurrency certainly encompasses much more, from executors and parallel streams to virtual threads. But in practice, protecting such shared data structures correctly is what most everyday Java code needs. The comparisons follow the same before/after format as the book [Java by Comparison](https://java.by-comparison.com) and are free to read.
            """, List.of()),
        new Category("Modern Java", """
            Although Java is believed dead over and over again, it keeps reinventing itself, one step at a time. Since Java 8, a new release ships every six months, and each one brings features that make everyday code shorter, clearer, and safer. Many teams still write Java as if it were 2014, simply because nobody showed them the newer way.

            The comparisons below each take a typical piece of pre-Java-8-style code and refine it with a feature from a later release, such as text blocks, the improved `Optional` API, switch expressions, records, and doc comments in Markdown. Like in the book [Java by Comparison](https://java.by-comparison.com), every comparison shows the code before and after, and explains why the newer version is better and what to watch out for when you adopt it in an existing code base.

            The examples are deliberately small, so you can try them right away in your own project. All you need is a current JDK.
            """, List.of()),
        new Category("Spring Boot",
            "A lot of Java developers don't write Java, they write Spring Boot.", List.of())
    );
  }

}
