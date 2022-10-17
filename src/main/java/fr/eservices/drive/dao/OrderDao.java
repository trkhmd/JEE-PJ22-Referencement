package fr.eservices.drive.dao;

import fr.eservices.drive.model.ArticleOrder;
import fr.eservices.drive.model.ArticleStatus;
import fr.eservices.drive.model.Order;

import java.util.List;

public interface OrderDao {

    Order findById(String id);
    void updateStatus(String id, ArticleOrder articleOrder, ArticleStatus articleStatus);
    String createOrder(List<ArticleOrder> articleOrder);
}
