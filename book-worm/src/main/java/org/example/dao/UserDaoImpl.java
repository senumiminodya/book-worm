package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao{
    @Override
    public boolean save(User user) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object save =  session.save(user);
        transaction.commit();
        session.close();
        return save !=null;
    }

    public boolean validateUser(String userName, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<User> query = session.createQuery("FROM User WHERE userName = :username AND password = :password", User.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);
        User user = query.uniqueResult();
        return user != null;
    }
    @Override
    public long count() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM User", Long.class);
        return query.uniqueResult();
    }

    @Override
    public int getMaxUserId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Integer> query = session.createQuery("SELECT MAX(id) FROM User", Integer.class);
        Integer maxId = query.uniqueResult();
        return maxId != null ? maxId : 0; // Return 0 if no users exist
    }
}
