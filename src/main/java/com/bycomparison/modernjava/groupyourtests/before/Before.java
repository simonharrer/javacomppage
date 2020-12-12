package com.bycomparison.modernjava.groupyourtests.before;

import org.junit.jupiter.api.Test;

public class Before {

}

// BEFORE:start
class SupplyTests {

  // START_HIGHLIGHT
  @Test
  void shouldUpdateBarcodeISBN() {
    // ...
  }
  // END_HIGHLIGHT

  @Test
  void shouldDoSomethingElse() {
    // ...
  }

  // START_HIGHLIGHT
  @Test
  void shouldUpdateBarcodeEAN() {
    // ...
  }
  // END_HIGHLIGHT

}
// BEFORE:end