package com.bycomparison.modernjava.inlineshortfilesintests.after;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

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

        // START_HIGHLIGHT
        var expectedJson = """
                {
                  "id": "1234", 
                  count: 5
                }""";
        // END_HIGHLIGHT
        assertEquals(expectedJson, actualJson, STRICT);
    }
}
// AFTER:end