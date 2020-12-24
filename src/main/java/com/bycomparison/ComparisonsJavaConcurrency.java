package com.bycomparison;

import java.util.List;

public class ComparisonsJavaConcurrency {

  /*
  Free Your Locks (always use finally, for all the resources like locks, executors, semaphore, etc. etc. etc.)
  Volatile
  https://javarevisited.blogspot.com/2015/05/top-10-java-multithreading-and.html
  *
  *
  *
   */

  public static List<Comparison> getComparisons() {
    var groupSharedValue = "Shared Value";
    var groupSharedList = "Shared List";
    return List
        .of(
            javaConcurrency("Protect Your Shared Memory", groupSharedValue,
                List.of("synchronized"),
                """
                    * shared memory, unprotected
                    * what can happen here when two threads count 100 times: expected 200, but basically any value could happen
                    * reading and writing 64 bit is not atomic, only 32 bit is
                    * lost updates
                    
                    ```java
                    var tmp = count; // read
                    var tmp = tmp + 1; // update
                    count = tmp; // write
                    ```
                    """, """
                    """
            ),
            javaConcurrency("Document for Concurrency", groupSharedValue,
                List.of("synchronized"),
                """
                    * without documentation takes some time to figure out if it really is thread-safe
                    """, """
                    * multiple variants: thread-safe, immutable, conditionally thread-safe, not thread-safe
                    * annotations from Java Concurrency in Practice
                    """
            ),
            javaConcurrency("Make Locks Explicit", groupSharedValue,
                List.of("synchronized"),
                """
                    * synchronized in the method may be short, but readers who aren't that familier with synchronized may get the wrong impression
                    * especially if the documentation is missing...
                    """, """
                    * just make it explicit
                    """
            ),
            javaConcurrency("Minimize Your Lock Scopes", groupSharedValue,
                List.of("synchronized"),
                """
                    * guard doesn't access shared state
                    * scope of `synchronized` is too large
                    """, """
                    * just make it explicit
                    """
            ),
            javaConcurrency("Hide Your Locks", groupSharedValue,
                List.of("synchronized"),
                """
                    * if `this` is the lock, everybody who has access to that object reference interfere
                    """, """
                    * with a private lock object, the monitor stays local
                    """
            ),
            javaConcurrency("Avoid Locks", groupSharedValue,
                List.of("AtomicLong", "compare-and-set"),
                """
                    * locks are bad
                    * may lead to deadlock
                    """, """
                    * compare-and-set allows lock free
                    * available since Java 5
                    * requires instruction by the CPU to work
                    
                    ```java
                    while(true) {
                       if(!count.compareAndSet(old, new)) {
                          old = count.get();
                          new = old + 1;
                       }
                    }
                    ```
                    """
            ),
            javaConcurrency("Avoid Active Waiting", groupSharedValue,
                List.of("LongAdder", "compare-and-set", "Striped64"),
                """
                    * AtomicLong is nonblocking (lock-free) but may result in lots of active waiting""",
                """
                    * LongAdder uses multiple atomatic longs (multiple stripes), one per thread. The sum of those stripes is the total. 
                    * Sum is not as exact as it is with the AtomicLong, but much higher performance if multiple threads increment simultaneously"""
            ),
            javaConcurrency("Protect Your Collections", groupSharedList,
                List.of("Collection", "List", "synchronized"),
                """
                    """,
                """
                    """),
            javaConcurrency("Use Synchronized Collections", groupSharedList,
                List.of("Collection", "List", "Collections.synchronized"),
                """
                    """,
                """
                    """),
            javaConcurrency("Use Concurrent Collections", groupSharedList,
                List.of("Collection", "List", "CopyOnWriteArrayList"),
                """
                   * using atomic methods with a thread-safe data structure may still lead to wrong results
                    """,
                """
                    * make that atomic which should be atomic
                    """),
            javaConcurrency("Separate Runnable From Thread", "Execution",
                List.of("Thread", "Runnable"),
                """
                    """,
                """
                    """),
            javaConcurrency("Favor Executor Over Thread", "Execution",
                List.of("Executor", "ExecutorService"),
                """
                    """,
                """
                    """),
            javaConcurrency("Make Sure to Clean Up Your Executor", "Execution",
                List.of("Executor", "ExecutorService", "finally"),
                """
                    """,
                """
                    """),
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
                    * Inefficient, since boolean is constantly checked"""
            ),
            Comparison.empty("Minimize Your Synchronized Blocks", "Locking", "Java Concurrency"),
            javaConcurrency("Guard Your Sleep", "Locking", List.of("sleep", "guarded-wait"), "", "")
        );
  }

  private static Comparison javaConcurrency(String name, String group, List<String> tags,
      String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText,
        "Java Concurrency");
  }


}
