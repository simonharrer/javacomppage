package com.bycomparison;

import java.util.List;
import java.util.stream.Collectors;

/** Renders sitemap.xml from all indexable pages, so new pages show up without manual edits. */
public class Sitemap {

  static String render(List<Page> pages) {
    var urls = pages.stream()
        .filter(Page::indexed)
        .map(Page::canonicalUrl)
        .distinct()
        .sorted()
        .map(url -> "  <url><loc>" + url + "</loc></url>")
        .collect(Collectors.joining("\n"));
    return """
        <?xml version="1.0" encoding="UTF-8"?>
        <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
        %s
        </urlset>
        """.formatted(urls);
  }
}
