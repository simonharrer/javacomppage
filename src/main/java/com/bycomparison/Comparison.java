package com.bycomparison;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static com.bycomparison.Generator.DIR;

public record Comparison(String name, String category, List<String> tags, String beforeCode,
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
        return name + " | " + new Category(category, List.of(this)).title();
    }

    public String previewCard() {
        if (Files.notExists(Path.of(DIR, uniqueName() + ".png"))) {
            return "https://java.by-comparison.com/img/cover.jpg";
        }

        return DIR + uniqueName() + ".png";
    }

    public String diff() {
        return Diff.withoutPreabmle(this);
    }

    public String link() {
        return uniqueName() + ".html";
    }

    public void generatePreviewCard() throws IOException {
        if (!beforeCode.isBlank()) {
            final String carbonScript = "echo  \"" + beforeCode + "\" | carbon-now -l " + DIR + " -t " + uniqueName();
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
            }
        }
    }
}
