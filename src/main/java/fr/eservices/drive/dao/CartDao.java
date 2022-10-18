package fr.eservices.drive.dao;

import fr.eservices.drive.web.dto.ArticleCart;

import java.util.List;

public interface CartDao {

    ArticleCart getById(String id);
    void add(ArticleCart articleCart);
    List<ArticleCart> getCartContent();
    void clear();
}
