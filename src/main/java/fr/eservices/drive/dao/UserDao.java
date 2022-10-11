package fr.eservices.drive.dao;

import fr.eservices.drive.model.User;

public interface UserDao {
    User find(String username, String password);
}
