package com.bycomparison;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static com.bycomparison.Generator.DIR;

public record Comparison(String name, String group, String category, List<String> tags, String beforeCode,
                         String beforeCodeHighlight, String beforeText,
                         String afterCode, String afterCodeHighlight, String afterText,
                         String language, String sources, boolean published, boolean book, String sample) {

    public String filename() {
        return uniqueName() + ".html";
    }

    private String uniqueName() {
        return name.replaceAll(" ", "-").replaceAll("/", "-").toLowerCase(Locale.ENGLISH);
    }

    public String title() {
        return name + " | " + new Category(category, "", List.of(this)).title(); //Fixme: What should the 2nd param be?!
    }

    public String previewCard() {
        if (Files.notExists(Path.of(DIR, uniqueName() + beforeCode.hashCode() + ".png"))) {
            return "https://java.by-comparison.com/img/cover.jpg";
        }

        return DIR + uniqueName() + beforeCode.hashCode() + ".png";
    }

    public String diff() {
        return Diff.withoutPreabmle(this);
    }

    public String link() {
        return uniqueName() + ".html";
    }

    public void generatePreviewCard() throws IOException {
        if (Files.exists(Path.of(DIR, uniqueName() + beforeCode.hashCode() + ".png"))) {
            return;
        }
        final String carbonScript = "echo  \"" + beforeCode + "\" | carbon-now -l " + DIR + " -t " + uniqueName() + beforeCode.hashCode();
        final Path carbonFile = Path.of(uniqueName() + ".sh");
        Files.write(carbonFile, carbonScript.getBytes());
        StringBuilder log = new StringBuilder("Error log for " + uniqueName() + System.lineSeparator());
        Process p = Runtime.getRuntime().exec("sh " + carbonFile.toString());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                log.append('\t').append(line).append(System.lineSeparator());
            }
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (p.exitValue() != 0) {
                System.err.println(log.toString());
            }
            p.destroy();

            // Delete potential outdated code PNGs
            Files.walk(Path.of(DIR))
                 .sorted(Comparator.reverseOrder())
                 .filter(path -> path.toString().startsWith(DIR + uniqueName()))
                 .filter(path -> !path.toString().endsWith(uniqueName() + beforeCode.hashCode() + ".png"))
                 .map(Path::toFile)
                 .forEach(File::delete);
        }
    }
}
