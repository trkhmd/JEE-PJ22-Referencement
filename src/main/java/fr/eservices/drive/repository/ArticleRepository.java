package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article,String> {

    List<Article> findAll();
    Pageable findAll(Pageable pageable);
    Article findById(String id);
    Article findByEan13(String ean13);
    List<Article> findByCategories(Category category);
}
