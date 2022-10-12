package fr.eservices.drive.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Order_table")
public class Order {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredOn;

    @OneToMany
    private List<ArticleOrder> articlesOrder = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(Date deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public List<ArticleOrder> getArticlesOrder() {
        return articlesOrder;
    }

    public void setArticlesOrder(List<ArticleOrder> articlesOrder) {
        this.articlesOrder = articlesOrder;
    }
}
