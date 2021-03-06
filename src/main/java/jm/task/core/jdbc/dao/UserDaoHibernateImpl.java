package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        openTransactionSession();

        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID) )";

        Session session = getSession();
        session.createSQLQuery(sql).executeUpdate();

        closeTransactionSession();
    }

    @Override
    public void dropUsersTable() {
        openTransactionSession();
        Session session = getSession();

        String sql = "DROP TABLE IF EXISTS users";

        session.createSQLQuery(sql).executeUpdate();

        closeTransactionSession();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        openTransactionSession();

        Session session = getSession();
        User user = new User(name, lastName, age);
        session.save(user);

        closeTransactionSession();
    }

    @Override
    public void removeUserById(long id) {
        openTransactionSession();
        Session session = getSession();

//        String sql = "DELETE FROM users WHERE ID = :id";
//
//        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setParameter("id", id);
//        query.executeUpdate();

        User user = (User) session.load(User.class, id);
        session.delete(user);
        session.flush();

        closeTransactionSession();
    }

    @Override
    public List<User> getAllUsers() {
        openTransactionSession();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";

        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql).addEntity(User.class);
        List<User> usersList = query.list();

        closeTransactionSession();

        return usersList;

    }

    @Override
    public void cleanUsersTable() {
        openTransactionSession();
        Session session = getSession();

        String sql = "DELETE FROM users";

        session.createSQLQuery(sql).executeUpdate();

        closeTransactionSession();
    }
}
