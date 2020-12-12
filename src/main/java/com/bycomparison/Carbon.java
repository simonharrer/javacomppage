package com.bycomparison;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Carbon {

  public static void generatePreviewCard(Path folder, String code, String name, String filename,
      String pngFilename) throws IOException {

    if (Files.exists(folder.resolve(pngFilename))) {
      return;
    }
    var carbonScript =
        "echo  \"%s\" | carbon-now --location %s --target %s".formatted(code, folder, filename);
    var carbonFile = Path.of(name + ".sh");
    Files.write(carbonFile, carbonScript.getBytes());
    var log = new StringBuilder("Error log for " + name + System.lineSeparator());
    var p = Runtime.getRuntime().exec("sh " + carbonFile.toString());

    try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
      String line;
      while ((line = br.readLine()) != null) {
        log.append('\t').append(line).append(System.lineSeparator());
      }
      p.waitFor();
    } catch (InterruptedException ignored) {
    } finally {
      if (p.exitValue() != 0) {
        System.err.println(log.toString());
      }
      p.destroy();

      // Delete potential outdated code PNGs
      Files.walk(folder)
          .sorted(Comparator.reverseOrder())
          .filter(path -> path.toString().startsWith(folder + name))
          .filter(path -> !path.toString().endsWith(pngFilename))
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }


}
