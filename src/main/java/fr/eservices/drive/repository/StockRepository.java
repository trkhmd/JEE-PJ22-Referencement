package fr.eservices.drive.repository;

import fr.eservices.drive.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, String> {
    Stock findById(String id);
}
