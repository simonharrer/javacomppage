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

  List<Image> split();

  boolean isComputable();

  Long findCraters();

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

class CraterScanner01 {

  long scan(Image image) {
    return image.findCraters();
  }

}

class CraterScanner02 {

  long scan(Image image) {
    var images = image.split();
    var result = 0L;
    for (var i : images) {
      result += i.findCraters();
    }
    return result;
  }

}


class CraterScanner1 {

  long scan(Image image) throws InterruptedException {
    var images = image.split();

    var threads = new ArrayList<CraterScannerThread>();
    for (Image i : images) {
      threads.add(new CraterScannerThread(i));
    }

    for (Thread thread : threads) {
      thread.start();
    }
    for (Thread thread : threads) {
      thread.join();
    }

    long result = 0L;
    for (CraterScannerThread thread : threads) {
      result += thread.foundCraters;
    }
    return result;
  }

}

class CraterScannerThread extends Thread {

  private final Image image;
  long foundCraters;

  CraterScannerThread(Image image) {
    this.image = image;
  }

  @Override
  public void run() {
    foundCraters = image.findCraters();
  }
}

class CraterScannerRunnable implements Runnable {

  private final Image image;
  long foundCraters;

  CraterScannerRunnable(Image image) {
    this.image = image;
  }

  @Override
  public void run() {
    foundCraters = image.findCraters();
  }
}

class CraterScanner2 {

  long scan(Image image) {
    var images = image.split();

    var threads = new ArrayList<CraterScannerThread>();
    for (Image i : images) {
      threads.add(new CraterScannerThread(i));
    }

    for (Thread thread : threads) {
      thread.start();
    }
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException ignored) {
      }
    }

    long result = 0L;
    for (CraterScannerThread thread : threads) {
      result += thread.foundCraters;
    }
    return result;
  }

}

class CraterScanner3 {

  long scan(Image image) {
    var images = image.split();

    var runnables = new ArrayList<CraterScannerRunnable>();
    for (Image i : images) {
      runnables.add(new CraterScannerRunnable(i));
    }

    var threads = new ArrayList<Thread>();
    for (Runnable runnable : runnables) {
      threads.add(new Thread(runnable));
    }

    for (Thread thread : threads) {
      thread.start();
    }
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException ignored) {
      }
    }

    long result = 0L;
    for (CraterScannerRunnable runnable : runnables) {
      result += runnable.foundCraters;
    }
    return result;
  }

}

class CraterScanner4 {

  ExecutorService executorService = ForkJoinPool.commonPool();

  long scan(Image image) {
    var images = image.split();

    var runnables = new ArrayList<CraterScannerRunnable>();
    for (Image i : images) {
      runnables.add(new CraterScannerRunnable(i));
    }

    var futures = new ArrayList<Future<?>>();
    for (Runnable runnable : runnables) {
      futures.add(executorService.submit(runnable));
    }
    for (Future<?> future : futures) {
      try {
        future.get();
      } catch (InterruptedException | ExecutionException ignored) {
      }
    }

    long result = 0L;
    for (CraterScannerRunnable runnable : runnables) {
      result += runnable.foundCraters;
    }
    return result;
  }

}

class CraterScanner41 {

  ExecutorService executorService = ForkJoinPool.commonPool();

  long scan(Image image) {
    var callables = image.splitIntoCallables();

    var futures = new ArrayList<Future<Long>>();
    for (var callable : callables) {
      futures.add(executorService.submit(callable));
    }

    long result = 0L;
    for (var future : futures) {
      try {
        result += future.get();
      } catch (InterruptedException | ExecutionException ignored) {
      }
    }
    return result;
  }

}

// skipped executorService.invokeAll because of interrupted exception issue

class CraterScanner8 {

  private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

  long scan(Image image) {
    return forkJoinPool.invoke(new Task(image));
  }

  static class Task extends RecursiveTask<Long> {

    private final Image image;

    Task(Image image) {
      this.image = image;
    }

    @Override
    protected Long compute() {
      if (image.isComputable()) {
        return image.findCraters();
      } else {
        var images = image.split();
        var tasks = new ArrayList<Task>();
        for (var image : images) {
          tasks.add(new Task(image));
        }
        invokeAll(tasks);
        long sum = 0L;
        for (var task : tasks) {
          sum += task.getRawResult();
        }
        return sum;
      }
    }
  }

}

class CraterScanner9 {

  long scan(Image image) {
    return image.split().parallelStream().map(Image::findCraters)
        .reduce(0L, Long::sum);
  }

}
