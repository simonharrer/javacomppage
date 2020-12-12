package com.bycomparison;

public record Dependencies(String next, String nextName, String previous, String previousName) {

  public static final Dependencies NO_DEPENDENCIES = new Dependencies("", "", "", "");
}
