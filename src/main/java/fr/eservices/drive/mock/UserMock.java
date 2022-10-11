package fr.eservices.drive.mock;

import fr.eservices.drive.dao.UserDao;
import fr.eservices.drive.model.User;
import fr.eservices.drive.model.UserType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Qualifier("mock")
public class UserMock implements UserDao {
    private HashMap<String, User> users = new HashMap<>();

    public UserMock() {
        {
            User admin = new User();
            admin.setUserType(UserType.ADMIN);
            admin.setUsername("admin");
            admin.setPassword("admin");
            users.put(admin.getUsername(), admin);
        }
        {
            User employee = new User();
            employee.setUserType(UserType.EMPLOYEE);
            employee.setUsername("employe");
            employee.setPassword("employe");
            users.put(employee.getUsername(), employee);
        }
        {
            User client = new User();
            client.setUserType(UserType.CUSTOMER);
            client.setUsername("client");
            client.setPassword("client");
            users.put(client.getUsername(), client);
        }
    }

    @Override
    public User find(String username, String password) {
        User user = users.get(username);
        if(user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}
