package com.bycomparison;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class MapReduceTest {


}


interface Image {

  List<ImagePart> splitIntoParts();
  List<ImageCallable> splitIntoCallables();

  List<ImageThread> splitIntoThreads();

  List<ImageRunnable> splitIntoRunnables();
}

interface ImageRunnable extends Runnable {

  long getFoundCraters();

}

interface ImagePart {

  long findCraters();
}

interface ImageCallable extends Callable<Long> {

  Long findCraters();
}

interface ImageThread {

  void start();

  void join() throws InterruptedException;

  long getFoundCraters();

  long findCraters();
}


class CraterScanner1 {

  long scan(Image image) throws InterruptedException {
    var threads = image.splitIntoThreads();

    for (var thread : threads) {
      thread.start();
    }
    for (var thread : threads) {
      thread.join();
    }

    long result = 0L;
    for (var thread : threads) {
      result += thread.getFoundCraters();
    }
    return result;
  }

}

class CraterScanner2 {

  long scan(Image image) {
    var threads = image.splitIntoThreads();

    for (var thread : threads) {
      thread.start();
    }
    for (var thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException ignored) {
      }
    }

    long result = 0L;
    for (var thread : threads) {
      result += thread.getFoundCraters();
    }
    return result;
  }

}

class CraterScanner3 {

  long scan(Image image) {
    var runnables = image.splitIntoRunnables();

    List<Thread> threads = new ArrayList<>();
    for (var runnable : runnables) {
      var thread = new Thread(runnable);
      thread.start();
      threads.add(thread);
    }
    for (var thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException ignored) {
      }
    }

    long result = 0L;
    for (var runnable : runnables) {
      result += runnable.getFoundCraters();
    }
    return result;
  }

}

class CraterScanner4 {

  ExecutorService executorService = ForkJoinPool.commonPool();

  long scan(Image image) {
    var runnables = image.splitIntoRunnables();

    List<Future<?>> futures = new ArrayList<>();
    for (var runnable : runnables) {
      var future = executorService.submit(runnable);
      futures.add(future);
    }
    for (var future : futures) {
      try {
        future.get();
      } catch (InterruptedException | ExecutionException ignored) {
      }
    }

    long result = 0L;
    for (var runnable : runnables) {
      result += runnable.getFoundCraters();
    }
    return result;
  }

}


class CraterScanner5 {

  ExecutorService executorService = ForkJoinPool.commonPool();

  long scan(Image image) throws InterruptedException, ExecutionException {
    List<ImagePart> parts = image.splitIntoParts();

    List<Callable<Long>> jobs = new ArrayList<>();
    for (var part : parts) {
      jobs.add(part::findCraters);
    }

    var futures = executorService.invokeAll(jobs);

    long result = 0L;
    for (var future : futures) {
      result += future.get();
    }

    return result;
  }

}

class CraterScanner8 {

  long scan(Image image) {
    List<ImagePart> parts = image.splitIntoParts();

    return ForkJoinPool.commonPool().invoke(new RecursiveTask<>() {
      @Override
      protected Long compute() {
        List<ImagePart> parts = image.splitIntoParts();

        return null;
      }
    });
  }

  static class Task extends RecursiveTask<Long> {

    private final ImagePart imagePart;

    Task(ImagePart imagePart) {
      this.imagePart = imagePart;
    }

    @Override
    protected Long compute() {
      return imagePart.findCraters();
    }
  }

}

class CraterScanner9 {

  long scan(Image image) {
    List<ImagePart> parts = image.splitIntoParts();

    return parts.parallelStream().map(ImagePart::findCraters).reduce(0L, Long::sum);
  }

}
