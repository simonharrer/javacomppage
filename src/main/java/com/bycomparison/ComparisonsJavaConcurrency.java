package com.bycomparison;

import java.util.List;

public class ComparisonsJavaConcurrency {

  /*
  Free Your Locks (always use finally, for all the resources like locks, executors, semaphore, etc. etc. etc.)
  Volatile
   */

  public static List<Comparison> getComparisons() {
    var groupSharedValue = "Shared Value";
    var groupSharedList = "Shared List";
    return List
        .of(
            javaConcurrency("Protect Your Shared Memory", groupSharedValue,
                List.of("synchronized"),
                """
                    """, """
                    """,
                "1,4", ""),
            javaConcurrency("Document for Concurrency", groupSharedValue,
                List.of("synchronized"),
                """
                    """, """
                    """,
                "1,4", ""),
            javaConcurrency("Make Locks Explicit", groupSharedValue,
                List.of("synchronized"),
                """
                    * synchronized in the method may be short, but readers who aren't that familier with synchronized may get the wrong impression
                    * especially if the documentation is missing...
                    """, """
                    * just make it explicit
                    """,
                "4,7,9,10,12", "4,7"),
            javaConcurrency("Hide Your Locks", groupSharedValue,
                List.of("synchronized"),
                """
                    * if `this` is the lock, everybody who has access to that object reference interfere
                    """, """
                    * with a private lock object, the monitor stays local
                    """,
                "4,7,9,10,12", "4,7"),
            javaConcurrency("Avoid Locks", groupSharedValue,
                List.of("AtomicLong", "compare-and-set"),
                """
                    * locks are bad
                    * may lead to deadlock
                    """, """
                    * compare-and-set allows lock free
                    * available since Java 5
                    * requires instruction by the CPU to work
                    """,
                "4,7,9,10,12", "4,7"),
            javaConcurrency("Avoid Active Waiting", groupSharedValue,
                List.of("LongAdder", "compare-and-set", "Striped64"),
                """
                    * AtomicLong is nonblocking (lock-free) but may result in lots of active waiting""",
                """
                    * LongAdder uses multiple atomatic longs (multiple stripes), one per thread. The sum of those stripes is the total. 
                    * Sum is not as exact as it is with the AtomicLong, but much higher performance if multiple threads increment simultaneously""",
                "4,7,9,10,12", "4,7"),
            javaConcurrency("Protect Your Collections", groupSharedList,
                List.of("synchronized", "List"),
                """
                    """,
                """
                    """, "", ""),
            javaConcurrency("Synchronize Your Collections", groupSharedList,
                List.of("Collections.synchronized", "List"),
                """
                    """,
                """
                    """, "", ""),
            javaConcurrency("Use Thread-Safe Collections", groupSharedList,
                List.of("CopyOnWriteArrayList"),
                """
                    """,
                """
                    """, "", ""),
            javaConcurrency("Separate Runnable From Thread", "Execution",
                List.of("Thread", "Runnable"),
                """
                    """,
                """
                    """, "", ""),
            javaConcurrency("Favor Executor Over Thread", "Execution",
                List.of("Executor", "ExecutorService"),
                """
                    """,
                """
                    """, "", ""),
            javaConcurrency("Make Sure to Clean Up Your Executor", "Execution",
                List.of("Executor", "ExecutorService", "finally"),
                """
                    """,
                """
                    """, "", ""),
            javaConcurrency("Guard your Waits", "Locking",
                List.of(),
                """
                    * `wait()` allows the Thread to suspend execution until it is woken up by a `notify()`.
                    * `synchronized` is necessary to prevent putting this Thread to `sleep` multiple times.
                    * `wait()` can be interrupted, but the value of `isReadyToLaunch` determines whether the `while` loop exits. Therefore, the `InterruptedException` can be ignored!                         
                    """,
                """
                    * Another Thread can modify `isReadyToLaunch`
                    * Countdown is only initiated when isReadyToLaunch becomes true
                    * Inefficient, since boolean is constantly checked""",
                "5",
                "4,5,7")
        );
  }

  private static Comparison javaConcurrency(String name, String group, List<String> tags,
      String beforeText, String afterText, String beforeCodeHighlight, String afterCodeHighlight) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText, beforeCodeHighlight,
        afterCodeHighlight, "Java Concurrency");
  }


}
