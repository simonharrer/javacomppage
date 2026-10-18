package com.bycomparison;

/**
 * One HTML page of the website. Every page, hand-written or generated, is rendered with the same
 * layout (views/layout.rocker.html); this record carries what differs per page.
 *
 * @param output     file name relative to the output directory, e.g. "story.html" or "site/java.html"
 * @param canonical  path of the canonical URL, e.g. "/story"
 * @param head       extra markup for the head, e.g. JSON-LD or a page stylesheet
 * @param scripts    extra markup before the closing body tag
 */
public record Page(String output, String title, String description, String canonical,
                   String ogType, String ogTitle, String ogDescription, String ogImage,
                   String robots, String head, String body, String scripts) {

  public static final String BASE_URL = "https://java.by-comparison.com";

  public String canonicalUrl() {
    return BASE_URL + canonical;
  }

  public String ogTitleOrTitle() {
    return ogTitle.isBlank() ? title : ogTitle;
  }

  public String ogDescriptionOrDescription() {
    return ogDescription.isBlank() ? description : ogDescription;
  }

  public String ogImageOrCover() {
    return ogImage.isBlank() ? BASE_URL + "/img/cover.jpg" : ogImage;
  }

  public String ogTypeOrWebsite() {
    return ogType.isBlank() ? "website" : ogType;
  }

  public boolean indexed() {
    return !robots.contains("noindex");
  }
}
