package fr.eservices.drive.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class ArticleOrder {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private int quantity;

    @ManyToOne
    private Article article;

    private ArticleStatus articleStatus;

    public boolean canReturn(Date date) {
        System.out.println(date);
        if(article.isPerishable() || articleStatus.equals(ArticleStatus.BEING_PREPARED) || articleStatus.equals(ArticleStatus.BACK_TO_STOCK) || articleStatus.equals(ArticleStatus.DELIVERED)) {
            return false;
        }

        if(articleStatus.equals(ArticleStatus.REFUSED)) return true;

        long diffInMillies = Math.abs(Date.from(Instant.now()).getTime() - date.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff <= 7;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }
}
