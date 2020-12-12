package com.bycomparison.modernjava.groupyourtests.after;

import org.junit.jupiter.api.Test;

public class After {

}


// AFTER:start
class SupplyTests {

  // START_HIGHLIGHT
  @Test
  void shouldUpdateBarcodeISBN() {
    // ...
  }

  @Test
  void shouldUpdateBarcodeEAN() {
    // ...
  }
  // END_HIGHLIGHT

  @Test
  void shouldDoSomethingElse() {
    // ...
  }
}
// AFTER:end