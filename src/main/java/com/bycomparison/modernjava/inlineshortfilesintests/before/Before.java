package com.bycomparison.modernjava.inlineshortfilesintests.before;

import static com.bycomparison.modernjava.inlineshortfilesintests.before.TestHelper.read;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class Before {

}

record Supply(String id, int count) {

  String toJson() {
    return "{}";
  }
}

class TestHelper {

  static String read(String name) {
    return null;
  }
}

// BEFORE:start
class SupplyTests {

  @Test
  void updateBarcode() throws JSONException {
    var supply = new Supply("1234", 5);

    var actualJson = supply.toJson();

    // START_HIGHLIGHT
    var expectedJson = read("/supply.json");
    // END_HIGHLIGHT
    assertEquals(expectedJson, actualJson, STRICT);
  }

}
// BEFORE:end