package fr.eservices.drive.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.drive.model.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Category findById(String id);
    List<Category> findAll();
    List<Category> findByName(String name);
    
}
