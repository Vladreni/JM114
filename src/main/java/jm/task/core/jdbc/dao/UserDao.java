package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    //create
    void saveUser(String name, String lastName, byte age);

    //read
    List<User> getAllUsers();

    //update

    //delete
    void removeUserById(long id);

    void cleanUsersTable();
}
