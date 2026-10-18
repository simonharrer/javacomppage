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

  public static List<Comparison> chapter1ProtectYourSharedMutableState() {
    var group = "Chapter 1: Protect Your Shared Mutable State";
    return List.of(javaConcurrency("Protect Your Shared Memory", group,
            List.of("synchronized"),
            """
                Welcome to this series of comparisons about strategies for reading and writing shared memory from multiple threads.

                Look at this `Counter` class. It has a `private` variable `count`, which can only be increased with the `increment()` method. If the `amount` is at least `1`, the method adds it to the previous value of `count`. This is perfectly fine Java code in a single-threaded world.

                Issues arise as soon as two or more threads call `increment()` at the same time. Imagine two threads calling `increment(1)` 100 times each on the same counter, starting at `0`. We would expect 200, but `count` can end up with basically any value up to 200. How can this happen?

                The problem hides behind `this.count += amount;`. Although it's a single line, the `+=` operator expands to three steps:

                ```java
                var tmp = count;    // read
                tmp = tmp + amount; // update
                count = tmp;        // write
                ```

                First, the thread *reads* the current value of `count` into a temporary variable. Second, it *updates* that temporary value. Third, it *writes* the result back into `count`. Such a sequence is called a critical section: it is only correct if no other thread interferes while it runs. With two threads, the steps can interleave like this:

                ```java
                var tmp1 = count;     // T1 read
                tmp1 = tmp1 + amount; // T1 update
                var tmp2 = count;     // T2 read
                tmp2 = tmp2 + amount; // T2 update
                count = tmp1;         // T1 write
                count = tmp2;         // T2 write
                ```

                T1 reads and updates, but before it can write, the scheduler lets T2 run. T2 reads the same old value and updates it. Then T1 writes its result, and T2 overwrites it with its own. The increment of T1 is gone: a **lost update**. The Java Memory Model allows this, since the program order only holds within a single thread: [*"compilers are allowed to reorder the instructions in either thread, when this does not affect the execution of that thread in isolation"*](https://docs.oracle.com/javase/specs/jls/se17/html/jls-17.html). And because `count` is a `long`, it gets even worse: the JVM may write the two 32-bit halves of a 64-bit value separately, so another thread could even read a value that was never written.

                Would you have thought that such innocent-looking code can lead to incorrect programs? On the right, we protect the shared memory so that only one thread at a time can be in the critical section.
                """, """
                The `synchronized` keyword on the method ensures that only one thread at a time can execute `increment()` on the same `Counter` object. A thread that wants to enter has to acquire the object's lock first. If another thread holds it, the thread waits until the lock is released.

                So the read, update, and write of `count` now run as one atomic unit, and lost updates can no longer happen: two threads calling `increment(1)` 100 times each always end at 200. `synchronized` also guarantees visibility, so a thread entering the method sees all writes made by the previous holder of the lock.

                This one keyword makes the code correct. The following comparisons make it clearer, faster, and safer to use.
                """
        ),
        javaConcurrency("Document for Concurrency", group,
            List.of("synchronized"),
            """
                The `Counter` is thread-safe now, but nothing tells its users. To find out, a reader has to inspect every method and check which fields are protected and how. That takes time, and with a larger class it's easy to get wrong. Someone adding a new method may not even notice that `count` must only be accessed while holding the lock.
                """, """
                Two short comments make the design explicit. The class comment states that `Counter` is thread-safe, so callers know they can share an instance between threads. The field comment states which lock guards `count`, so maintainers know the rule they have to follow when they change the class.

                These comments mirror the annotations `@ThreadSafe` and `@GuardedBy` from the book *Java Concurrency in Practice*. Useful categories for the class comment are thread-safe, immutable, conditionally thread-safe, and not thread-safe. Documenting a class as not thread-safe is just as valuable, because it tells users to protect it themselves.
                """
        ),
        javaConcurrency("Make Locks Explicit", group,
            List.of("synchronized"),
            """
                A `synchronized` modifier on the method is short, but it hides what actually happens. Readers who aren't familiar with the details may not realize that the method locks on `this`, that the lock spans the whole method body, or that other `synchronized` methods of the same object share the same lock. Without documentation, this is even harder to see.
                """, """
                A `synchronized (this)` block shows the lock and its scope right in the code: everything inside the braces runs while holding the lock on `this`, everything outside doesn't. The behavior is exactly the same as before.

                Making the lock explicit is the first step to improving it. Now you can see what the lock covers, which allows you to shrink its scope and to replace `this` with a dedicated lock object, as the next comparisons show.
                """
        ),
        javaConcurrency("Minimize Your Lock Scopes", group,
            List.of("synchronized"),
            """
                The `synchronized` block covers the whole method, including the check of `amount`. But that check only reads the parameter, which belongs to the calling thread alone. It doesn't touch any shared state, so there's no need to hold the lock while running it. Holding a lock longer than necessary makes other threads wait for nothing.
                """, """
                The validation now runs before the lock is acquired, and the `synchronized` block only covers the critical section: the update of `count`. Invalid calls fail without ever touching the lock, and valid calls hold it for the shortest possible time.

                Here, the gain is tiny. But in real code, the part outside the lock can be expensive: parsing input, logging, or calling other services. The rule stays the same: lock exactly the code that accesses shared state, and nothing more.
                """
        ),
        javaConcurrency("Hide Your Locks", group,
            List.of("synchronized"),
            """
                The counter locks on `this`. That's the default, but `this` is a public reference: anyone who holds a reference to the counter can also synchronize on it. Code elsewhere, which you neither know nor control, might hold that lock for a long time, or acquire it in a different order together with other locks, and cause unexpected waits or even a deadlock.
                """, """
                The counter now locks on a `private final` object that nobody outside the class can reach. So no other code can interfere with the counter's locking, and all reasoning about the lock stays within this one class. Declaring the lock `final` ensures that all threads always synchronize on the same object.

                Don't forget to update the documentation: `count` is now guarded by `lock`, not by `this`.
                """
        ),
        javaConcurrency("Use Fair Locks", group,
            List.of("ReentrantLock"),
            """
                The intrinsic lock behind `synchronized` makes no promise about the order in which waiting threads get the lock. When it's released, any waiting thread may get it next, even one that just arrived. Under high contention, an unlucky thread can wait much longer than others, in theory indefinitely.
                """, """
                A `ReentrantLock` behaves like `synchronized`, but offers more options. Passing `true` to the constructor makes it fair: waiting threads get the lock in the order they asked for it, first come, first served. No thread starves anymore.

                Fairness comes at a cost, since handing over the lock in strict order reduces throughput. And the lock is no longer released automatically: you have to call `unlock()` yourself, in a `finally` block, so that the lock is released even when an exception occurs. Use a fair lock only when the order actually matters.
                """
        ),
        javaConcurrency("Avoid Locks", group,
            List.of("AtomicLong"),
            """
                Locks are a heavy tool for such a simple task. Every call has to acquire and release the lock, threads that can't get it are suspended and woken up again, which costs performance. And as soon as several locks are involved, a wrong order can lead to deadlocks. All this just to add a number.
                """, """
                An `AtomicLong` makes the counter thread-safe without any lock. `addAndGet()` performs the read, update, and write as a single atomic operation, backed by special instructions of the CPU, such as compare-and-set. Conceptually, it works like this loop, which retries until no other thread has changed the value in between:

                ```java
                long old;
                do {
                  old = count.get();
                } while (!count.compareAndSet(old, old + amount));
                ```

                The code is shorter, there's no lock to forget to release, and no thread is ever suspended. Atomic variables have been available since Java 5. They work best for a single variable; once several values have to change together, you're back to locks.
                """
        ),
        javaConcurrency("Avoid Active Waiting", group,
            List.of("LongAdder"),
            """
                The `AtomicLong` is lock-free, but all threads still update one and the same memory location. When many threads increment at the same time, they keep competing for it: compare-and-set attempts fail and have to be retried, and the CPU cores keep passing the same cache line back and forth. The threads are busy, but much of that work is waiting in disguise.
                """, """
                A `LongAdder` spreads the updates over several internal cells. Under contention, different threads add to different cells, so they rarely get in each other's way. Reading the value with `sum()` adds up all cells.

                This scales much better when many threads count at the same time, at the cost of more memory. And `sum()` is not an atomic snapshot: while other threads keep adding, the result may miss the very latest increments. That's perfectly fine for counters and statistics, which are written often and read rarely. When you need exact values for decisions, such as generating unique IDs, stick with `AtomicLong`.
                """
        ));
  }

  public static List<Comparison> chapter2ProtectYourSharedMutableCollection() {
    var group = "Chapter 2: Protect Your Shared Mutable Collection";
    return List.of(javaConcurrency("Protect Your Collections", group,
            List.of("synchronized"),
            """
                The `Crew` keeps a list of unique member names. It's documented as not thread-safe, and for good reason: an `ArrayList` must not be modified by several threads at the same time, it can end up in a corrupt state.

                Declaring the field `final` doesn't help here: it only prevents assigning another list, the list itself can still change. And `add()` has a subtle problem of its own. It first checks whether the name is in the list and then adds it. Two threads adding the same name can both pass the check before either adds, and the name ends up in the list twice.
                """,
            """
                Both methods now synchronize on the list, and the documentation says so. Inside `add()`, the check and the insertion form one critical section, so no other thread can add the same name in between. `size()` synchronizes as well, so it never reads the list in the middle of a change and always sees the latest state.

                This is the standard way to protect any shared state: one lock, documented, held for every access. As in the previous chapter, the lock is a `private final` field that no outside code can reach.
                """),
        javaConcurrency("Use Synchronized Collections", group,
            List.of("Collections.synchronized"),
            """
                Every method that touches the list has to remember to synchronize. With two methods, that's manageable, but each new method is another chance to forget the lock, and forgetting it doesn't cause a compile error, just rare and hard-to-reproduce bugs.
                """,
            """
                `Collections.synchronizedList()` wraps the list so that every single method call is synchronized on the wrapper. So `size()` no longer needs its own `synchronized` block.

                But `add()` still does. Each call to `contains()` and `add()` is thread-safe on its own, but another thread can still run between the two. The check and the insertion have to happen atomically, so they still need a block that synchronizes on the list. Since the wrapper uses itself as its lock, synchronizing on `members` works together with the wrapper's own locking.
                """),
        javaConcurrency("Use Concurrent Collections", group,
            List.of("CopyOnWriteArrayList"),
            """
                Even with a synchronized collection, the check-then-add sequence still needs an explicit `synchronized` block. Thread-safe methods don't make a sequence of calls thread-safe, and that's easy to miss.
                """,
            """
                A `CopyOnWriteArrayList` offers exactly the operation we need: `addIfAbsent()` checks and adds atomically in one call. Make atomic what should be atomic, and the explicit lock disappears completely.

                The name tells you the trade-off: every modification copies the whole underlying array, while reads need no locking at all. That makes it a great fit for small lists that are read often and changed rarely, like the members of a crew. For large or frequently changing collections, look at other classes in `java.util.concurrent`, such as `ConcurrentHashMap`, whose key set offers the same set semantics.
                """));
  }

  public static List<Comparison> chapter3VirtualThreads() {
    var group = "Chapter 3: Solve Your Problem in Parallel";
    return List.of(javaConcurrency("Favor Virtual Threads for Blocking Tasks", group,
        List.of("Virtual Threads", "ExecutorService", "Java 21"),
        """
            The collector asks every station for its latest reading. Each call blocks while it waits for the network, so the calls run in parallel on a thread pool.

            But platform threads are expensive: each one is backed by an operating system thread with its own large stack. So the pool is capped at 100 threads. With 10,000 stations, 9,900 tasks wait in the queue while the 100 threads mostly sit idle, waiting for answers. Choosing the pool size is guesswork: too small, and requests queue up; too large, and memory runs out. And the executor has to be shut down manually in a `finally` block.
            """,
        """
            Since Java 21 (JEP 444), `Executors.newVirtualThreadPerTaskExecutor()` starts a new virtual thread for every task. Virtual threads are cheap, because the JVM manages them instead of the operating system. When a virtual thread blocks on I/O, the JVM sets it aside and uses the underlying carrier thread for other work. So all 10,000 requests can wait at the same time, and there's no pool size left to tune.

            The rest of the code stays the same, since virtual threads are ordinary `Thread` objects that work with `Future` as before. And since Java 19, an `ExecutorService` is `AutoCloseable`: closing it waits for all submitted tasks, so try-with-resources replaces the manual `shutdown()`.

            Virtual threads speed up tasks that mostly wait, not CPU-heavy computations. Don't pool them, just create a new one per task. If a resource is scarce, such as database connections, limit concurrent access with a `Semaphore` instead of a small thread pool.
            """),
        javaConcurrency("Structure Your Concurrent Tasks", group,
            List.of("Structured Concurrency", "Preview", "Java 25"),
            """
                The launch check needs two things, the weather and the crew status, so it fetches both in parallel with an executor and combines the results.

                It looks harmless, but the two tasks have no relationship to each other or to the method that started them. If fetching the crew status fails right away, `weather.get()` doesn't notice: it keeps waiting until the weather arrives, and only then does `crew.get()` report the failure. The `catch` block has to cancel both tasks by hand, and forgetting it leaves threads running in the background, doing work that nobody needs anymore. If the thread calling `check()` gets interrupted, the subtasks keep running unless the code cancels them as well. And in a thread dump, nothing shows which method these tasks belong to.
                """,
            """
                Structured concurrency treats a group of related tasks as a single unit of work. `StructuredTaskScope.open()` creates a scope, `fork()` starts each subtask in its own virtual thread, and `join()` waits for all of them. The try-with-resources block guarantees that no subtask outlives the scope.

                By default, the scope fails as soon as one subtask fails: it cancels the other subtasks and `join()` throws a `FailedException` with the original cause. So if fetching the crew status fails, the weather request is cancelled right away. If the thread running `check()` is interrupted, the subtasks are cancelled too. The manual cancellation code is gone, and thread dumps show the subtasks as children of the scope.

                Different policies are available as so-called joiners, for example to return the first successful result and cancel the rest. Note that the structured concurrency API is still a preview in Java 25 (JEP 505) and has changed between releases. To use it, compile and run with `--enable-preview`.
                """),
        javaConcurrency("Pass Context with Scoped Values", group,
            List.of("ScopedValue", "ThreadLocal", "Java 25"),
            """
                Mission control needs to know which astronaut sent the command, deep down in the `Dispatcher`, without passing the astronaut through every method in between. A `ThreadLocal` is the classic solution: `handle()` stores the astronaut for the current thread, and the dispatcher reads it.

                But a `ThreadLocal` is a mutable global variable per thread. Any code can call `set()` at any time, so you can't tell where the value came from. It stays set until someone calls `remove()`: forget the `finally` block, and in a thread pool, the next request on the same thread runs as the previous astronaut, a real security bug. And with a million virtual threads, each holding its own copies of thread-local values, the memory adds up quickly.
                """,
            """
                Since Java 25 (JEP 506), a `ScopedValue` passes such context in a safer way. `ScopedValue.where()` binds the astronaut for exactly the duration of `run()`. Within that block, including all the methods it calls, `CURRENT.get()` returns the astronaut. Afterwards, the binding is gone automatically. There's no `remove()` to forget, so the value can never leak into the next request.

                Scoped values are immutable: there is no `set()` method, so code deep down the call stack can't change the context for its callers. A nested `where()` can rebind the value for an inner block, but only for that block. Scoped values are also cheap to read and to inherit: subtasks forked in a `StructuredTaskScope` see the same binding without copying it.

                Note that the dispatcher didn't change at all. Reading `CURRENT.get()` outside a binding throws a `NoSuchElementException`, so a missing context fails fast instead of silently returning `null`.
                """));
  }

  public static List<Comparison> chapter3SolveYourProblemInParallel() {
    var group = "Chapter 3: Solve Your Problem in Parallel";
    return List.of(javaConcurrency("Design for Concurrency", group, List.of(),
            "", ""),
        javaConcurrency("Parallelize with Threads", group, List.of(),
            "", ""),
        javaConcurrency("Separate Runnable From Thread", group,
            List.of("Thread", "Runnable"),
            """
                """,
            """
                """),
        javaConcurrency("Favor Executor Over Thread", group,
            List.of("Executor", "ExecutorService"),
            """
                """,
            """
                """),
        javaConcurrency("Guard your Waits", group,
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
        javaConcurrency("Guard Your Sleep", group, List.of("sleep", "guarded-wait"),
            "", ""));
  }

  private static Comparison javaConcurrency(String name, String group, List<String> tags,
      String beforeText, String afterText) {
    return Comparison.javaComparison(name, group, tags, beforeText, afterText,
        "Java Concurrency");
  }
}
