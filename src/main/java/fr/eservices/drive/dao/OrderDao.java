package fr.eservices.drive.dao;

import fr.eservices.drive.model.ArticleOrder;
import fr.eservices.drive.model.ArticleStatus;
import fr.eservices.drive.model.Order;

public interface OrderDao {

    Order findById(String id);
    void updateStatus(String id, ArticleOrder articleOrder, ArticleStatus articleStatus);

}
