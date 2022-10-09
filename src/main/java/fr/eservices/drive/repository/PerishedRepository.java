package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Perishable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PerishedRepository extends CrudRepository<Perishable,String> {
    List<Perishable> findByArticle(Article article);
    List<Perishable> findAll();
    Perishable findById(String id);
}
