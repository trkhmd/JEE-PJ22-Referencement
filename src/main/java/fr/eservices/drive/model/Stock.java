package fr.eservices.drive.model;

import javax.persistence.*;

@Entity
public class Stock {
    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    private Article article;
    private int stock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
