package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    Product findById(String id);
    List<Product> findAll();
}
