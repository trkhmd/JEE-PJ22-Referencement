package fr.eservices.drive.model;

import fr.eservices.drive.web.dto.ArticleEntry;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Cart {

    List<ArticleEntry> articles;

    public Cart(){
        articles = new ArrayList<>();
    }

    public void addArticleToCart(ArticleEntry art){
        this.articles.add(art);
    }

    public List<ArticleEntry> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleEntry> articles) {
        this.articles = articles;
    }
}
