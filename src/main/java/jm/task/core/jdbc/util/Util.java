package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jmpreproject";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        Connection connection = null;

        //System.out.println("Registering JDBC driver...");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load class.");
            e.printStackTrace();
        }

        //System.out.println("Creating database connection...");
        try {
            connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            System.out.println("Connection OK!");
        } catch (SQLException th) {
            System.out.println("Connection ERROR!");
            th.printStackTrace();
        }

        return connection;
    }

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private Session session;
    private Transaction transaction;

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Session openSession() {
        return getSessionFactory().openSession();
    }

    public Session openTransactionSession() {
        session = openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void closeTransactionSession() {
        transaction.commit();
        closeSession();
    }
}
