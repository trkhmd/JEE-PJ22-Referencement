package fr.eservices.drive.dao;

import fr.eservices.drive.web.dto.ArticleCart;

import java.util.List;

public interface CartDao {

    void add(ArticleCart articleCart);
    List<ArticleCart> getCartContent();
    void clear();
}
