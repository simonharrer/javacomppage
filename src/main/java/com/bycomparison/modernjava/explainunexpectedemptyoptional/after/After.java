package com.bycomparison.modernjava.explainunexpectedemptyoptional.after;

import java.util.NoSuchElementException;
import java.util.Optional;

public class After {

}


class Supply {

  void setBarcode(String barcode) {
  }
};

interface SupplyRepository {

  Optional<Supply> findById(String id);

  void save(Supply supply);
}

// AFTER:start
class CandyService {

  private SupplyRepository supplyRepository;

  void updateBarcode(String id, String newBarcode) {
    // START_HIGHLIGHT
    var supply = supplyRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Unknown " + id));
    // END_HIGHLIGHT
    supply.setBarcode(newBarcode);
    supplyRepository.save(supply);
  }
}
// AFTER:end