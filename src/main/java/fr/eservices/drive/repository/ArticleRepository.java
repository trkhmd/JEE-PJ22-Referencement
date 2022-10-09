package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article,String> {
    Article findByEan13(String id);
    Pageable findAll(Pageable pageable);
    List<Article> findAll();
}
