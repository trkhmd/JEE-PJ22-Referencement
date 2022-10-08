package fr.eservices.drive.repository;

import fr.eservices.drive.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock,String> {

    Stock findById(String id);
    List<Stock> findAll();
}
