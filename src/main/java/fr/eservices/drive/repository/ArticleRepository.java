package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository<T extends Article> extends CrudRepository<T,String> {
    T findByEan13(String id);
    Pageable findAll(Pageable pageable);
    List<T> findAll();
}
