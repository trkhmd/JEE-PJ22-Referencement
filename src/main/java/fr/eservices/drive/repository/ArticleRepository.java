package fr.eservices.drive.repository;

import fr.eservices.drive.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface ArticleRepository
extends JpaRepository<Article,Integer>
{

    Article findById(Integer id);

    Page<Article> findAll(Pageable pageable);


}
