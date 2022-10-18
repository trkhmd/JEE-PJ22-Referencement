package fr.eservices.drive.web.dto;

import fr.eservices.drive.model.Article;

public class ArticleCart {
    private String stockId;
    private Article article;
    private int quantity;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
