package com.bycomparison.modernjava.inlineshortfilesintests.after;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class After {

}


record Supply(String id, int count) {

  String toJson() {
    return "{}";
  }
}

// AFTER:start
class SupplyTests {

  @Test
  void updateBarcode() throws JSONException {
    var supply = new Supply("1234", 5);

    var actualJson = supply.toJson();

    var expectedJson = """
        {
          "id": "1234", 
          count: 5
        }""";
    assertEquals(expectedJson, actualJson, STRICT);
  }

}
// AFTER:end