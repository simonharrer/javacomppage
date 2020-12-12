package com.bycomparison.modernjava.nestyourtests.after;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class After {

}


// AFTER:start
class SupplyTests {

  // START_HIGHLIGHT
  @Nested
  static class BarcodeUpdateTests {
    // END_HIGHLIGHT

    @Test
    void shouldUpdateBarcodeISBN() {
      // ...
    }

    @Test
    void shouldUpdateBarcodeEAN() {
      // ...
    }
    // START_HIGHLIGHT
  }
  // END_HIGHLIGHT

  @Test
  void shouldDoSomethingElse() {
    // ...
  }
}
// AFTER:end