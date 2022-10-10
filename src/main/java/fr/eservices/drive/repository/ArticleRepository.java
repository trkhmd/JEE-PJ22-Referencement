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
    List<Article> findByCategories(Category cat);
    List<Article> findByNameLike(String nameFilter);
    List<Article> findByEan13Like(String refFilter);
    List<Article> findByCategoriesAndNameLikeAndEan13Like(Category cat, String nameFilter, String refFilter);
    List<Article> findByCategoriesAndNameLike(Category cat, String nameFilter);
    List<Article> findByCategoriesAndEan13Like(Category cat, String refFilter);
    List<Article> findByNameLikeAndEan13Like(String nameFilter, String refFilter);

}