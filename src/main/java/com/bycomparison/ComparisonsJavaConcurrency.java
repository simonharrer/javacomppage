package com.bycomparison;

import java.util.List;

public class ComparisonsJavaConcurrency {

  public static List<Comparison> getComparisons() {
    return List
        .of(
            new Comparison("Document for Concurrency", "Documentation", "Java Concurrency",
                List.of(),
                Code.parseBeforeCodePart("javaconcurrency/documentforconcurrency"), "", "",
                Code.parseAfterCodePart("javaconcurrency/documentforconcurrency"), "1,4", "",
                "java", "", true, false, ""),
            new Comparison("Guard your Waits", "Locking", "Java Concurrency",
                List.of(),
                Code.parseBeforeCodePart("javaconcurrency/guardyourwaits"), "5",
                Markdown.toHtml("""
                    * Another Thread can modify `isReadyToLaunch`
                    * Countdown is only initiated when isReadyToLaunch becomes true
                    * Inefficient, since boolean is constantly checked"""),
                Code.parseAfterCodePart("javaconcurrency/guardyourwaits"), "4,5,7",
                Markdown.toHtml("""
                    * `wait()` allows the Thread to suspend execution until it is woken up by a `notify()`.
                    * `synchronized` is necessary to prevent putting this Thread to `sleep` multiple times.
                    * `wait()` can be interrupted, but the value of `isReadyToLaunch` determines whether the `while` loop exits. Therefore, the `InterruptedException` can be ignored!                         
                    """),
                "java", "", true, false, "")
        );
  }


}
