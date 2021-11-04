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

    public static List<Comparison> getGroupExecutionComparisons() {
        var groupExecution = "Solve Your Problem in Parallel";
    return List.of(javaConcurrency("Design for Concurrency", groupExecution, List.of(),
            "", ""),
        javaConcurrency("Parallelize with Threads", groupExecution, List.of(),
            "", ""),
        javaConcurrency("Separate Runnable From Thread", groupExecution,
            List.of("Thread", "Runnable"),
            """
                """,
            """
                """),
        javaConcurrency("Favor Executor Over Thread", groupExecution,
            List.of("Executor", "ExecutorService"),
            """
                """,
            """
                """),
        javaConcurrency("Guard your Waits", groupExecution,
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
        javaConcurrency("Guard Your Sleep", groupExecution, List.of("sleep", "guarded-wait"),
            "", ""));
  }

  public static List<Comparison> getGroupSharedListComparisons() {
    var groupSharedList = "Chapter 2: Protect Your Shared Mutable Collection";
    return List.of(javaConcurrency("Protect Your Collections", groupSharedList,
            List.of("synchronized"),
            """
                """,
            """
                """),
        javaConcurrency("Use Synchronized Collections", groupSharedList,
            List.of("Collections.synchronized"),
            """
                """,
            """
                """),
        javaConcurrency("Use Concurrent Collections", groupSharedList,
            List.of("CopyOnWriteArrayList"),
            """
                * using atomic methods with a thread-safe data structure may still lead to wrong results
                 """,
            """
                * make that atomic which should be atomic
                """));
  }

  public static List<Comparison> getGroupSharedValueComparisons() {
        var groupSharedValue = "Chapter 1: Protect Your Shared Mutable State";
    return List.of(javaConcurrency("Protect Your Shared Memory", groupSharedValue,
            List.of("synchronized"),
            """
                * shared memory, unprotected
                * Can be read or written by multiple threads
                * what can happen here when two threads count 100 times: expected 200, but basically any value could happen
                * reading and writing 64 bit is not atomic, only 32 bit is
                * lost updates due to critical section:
                                         
                ```java
                var tmp = count; // read
                var tmp = tmp + 1; // update
                count = tmp; // write
                ```
                """, """
                    * `synchonized` ensures that only one thread can access the method at a time.
                    * lost update can not happen anymore: the code is correct
                    * 
                    """
        ),
        javaConcurrency("Document for Concurrency", groupSharedValue,
            List.of("synchronized"),
            """
                * Knowing if a method is thread safe is very important!
                * Without documentation, it takes some time to figure out if it really is thread-safe
                """, """
                    * annotations from Java Concurrency in Practice
                    * help to safely use the classes
                    * multiple variants: thread-safe, immutable, conditionally thread-safe, not thread-safe
                    """
        ),
        javaConcurrency("Make Locks Explicit", groupSharedValue,
            List.of("synchronized"),
            """
                * `synchronized` in the method may be short, but readers who aren't that familiar with `synchronized` may get the wrong impression
                * especially if the documentation is missing...
                """, """
                    * You should make the scope of the `synchronized` block explicit
                    """
        ),
        javaConcurrency("Minimize Your Lock Scopes", groupSharedValue,
            List.of("synchronized"),
            """
                * guard doesn't access shared state
                * scope of `synchronized` is too large
                """, """
                    * reduce the lock to the critical section!
                    """
        ),
        javaConcurrency("Hide Your Locks", groupSharedValue,
            List.of("synchronized"),
            """
                * as a default, `this` is the reference of the lock
                * => everybody who has access to that object reference can interfere
                """, """
                    * with a `private` lock object, the lock monitor stays local
                    """
        ), javaConcurrency("Use Fair Locks", groupSharedValue,
            List.of("ReentrantLock"),
            """
                * unfair, first-come first-served
                """, """
                    * same behavior as `synchronized`
                    * but also fair (see `true` parameter in the constructor)
                    * this comes at a slight cost
                    """
        ),
        javaConcurrency("Avoid Locks", groupSharedValue,
            List.of("AtomicLong"),
            """
                * locks are undesirable:
                  - performance issues
                  - deadlocks
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
            List.of("LongAdder"),
            """
                * AtomicLong is nonblocking (lock-free) but may result in lots of active waiting""",
            """
                * LongAdder uses multiple atomatic longs (multiple stripes), one per thread. The sum of those stripes is the total. 
                * Sum is not as exact as it is with the AtomicLong, but much higher performance if multiple threads increment simultaneously"""
        ));
  }

  private static Comparison javaConcurrency(String name, String group, List<String> tags,
      String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText,
        "Java Concurrency");
  }
}
