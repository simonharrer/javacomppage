package com.bycomparison.modernjava.modeldatawithrecords.before;

import java.util.Objects;

public class Before {}

// BEFORE:start
// START_HIGHLIGHT
final class Coordinates {

  private final double latitude;
  private final double longitude;

  Coordinates(double latitude, double longitude) {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("bad latitude");
    }
    this.latitude = latitude;
    this.longitude = longitude;
  }

  double getLatitude() {
    return latitude;
  }

  double getLongitude() {
    return longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Coordinates)) {
      return false;
    }
    Coordinates that = (Coordinates) o;
    return Double.compare(latitude, that.latitude) == 0
        && Double.compare(longitude, that.longitude) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude);
  }

  @Override
  public String toString() {
    return "Coordinates[latitude=" + latitude + ", longitude=" + longitude + "]";
  }
}
// END_HIGHLIGHT
// BEFORE:end
