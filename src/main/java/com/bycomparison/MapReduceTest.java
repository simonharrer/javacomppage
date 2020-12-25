package com.bycomparison;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MapReduceTest {


}


interface Image {

  List<ImagePart> splitIntoParts();
}

interface ImagePart {

  void start();

  void join() throws InterruptedException;

  long getFoundCraters();

  long findCraters();
}


class CraterScanner1 {

  long scan(Image image) throws InterruptedException {
    List<ImagePart> parts = image.splitIntoParts();

    for (var part : parts) {
      part.start();
    }
    for (var part : parts) {
      part.join();
    }

    long result = 0L;
    for (var part : parts) {
      result += part.getFoundCraters();
    }
    return result;
  }

}

class CraterScanner2 {

  long scan(Image image) {
    List<ImagePart> parts = image.splitIntoParts();

    for (var part : parts) {
      part.start();
    }
    for (var part : parts) {
      try {
        part.join();
      } catch (InterruptedException ignored) {
      }
    }

    long result = 0L;
    for (var part : parts) {
      result += part.getFoundCraters();
    }
    return result;
  }

}


class CraterScanner3 {

  ExecutorService executorService;

  long scan(Image image) throws InterruptedException {
    List<ImagePart> parts = image.splitIntoParts();


    List<Callable<Long>> jobs = new ArrayList<>();
    for (var part : parts) {
      jobs.add(part::findCraters);
    }
    executorService.invokeAll(jobs);

    long result = reduce(parts);
    return result;
  }

  private long reduce(List<ImagePart> parts) {
    long result = 0L;
    for (var part : parts) {
      result += part.getFoundCraters();
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
