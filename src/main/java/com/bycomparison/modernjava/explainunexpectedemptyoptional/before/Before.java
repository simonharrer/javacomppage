package com.bycomparison.modernjava.explainunexpectedemptyoptional.before;

import java.util.Optional;

public class Before {

}

class Supply {

  void setBarcode(String barcode) {}
};
interface SupplyRepository {
  Optional<Supply> findById(String id);
  void save(Supply supply);
}

// BEFORE:start
class CandyService {

  private SupplyRepository supplyRepository;

  void updateBarcode(String id, String newBarcode) {
    var supply = supplyRepository.findById(id).orElseThrow();
    supply.setBarcode(newBarcode);
    supplyRepository.save(supply);
  }
}
// BEFORE:end