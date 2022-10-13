package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,String> {

    Page<Article> findAll(Pageable pageable);
    Article findByEan13(String ean13);
    Page<Article> findByCategories(Category cat, Pageable pageable);
    Page<Article> findByNameIgnoreCaseLike(String nameFilter, Pageable pageable);
    Page<Article> findByEan13(String refFilter, Pageable pageable);
    Page<Article> findByEan13Like(String refFilter, Pageable pageable);
    Page<Article> findByCategoriesAndNameIgnoreCaseLikeAndEan13Like(Category cat, String nameFilter, String refFilter, Pageable pageable);
    Page<Article> findByCategoriesAndNameIgnoreCaseLike(Category cat, String nameFilter, Pageable pageable);
    Page<Article> findByCategoriesAndEan13Like(Category cat, String refFilter, Pageable pageable);
    Page<Article> findByNameIgnoreCaseLikeAndEan13Like(String nameFilter, String refFilter, Pageable pageable);

}