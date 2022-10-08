package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,String> {
}
