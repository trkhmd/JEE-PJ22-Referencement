package fr.eservices.drive.dao;

import fr.eservices.drive.model.Order;

public interface OrderDao {

    Order findById(String id);

}
