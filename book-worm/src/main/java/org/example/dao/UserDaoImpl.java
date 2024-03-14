package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.Branch;
import org.example.entity.User;
import org.example.indb.DB;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public boolean save(User user, Session session) throws SQLException {
        Object save =  session.save(user);
        return save !=null;
    }

    public boolean validateUser(String userName, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<User> query = session.createQuery("FROM User WHERE userName = :username AND password = :password", User.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);
        User user = query.uniqueResult();
        DB.loggedUserId = user.getId();
        DB.loggedUserName = user.getUserName();
        return user != null;
    }
    @Override
    public long count(Session session) {
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM User", Long.class);
        return query.uniqueResult();
    }

    @Override
    public int getMaxUserId(Session session) {
        Query<Integer> query = session.createQuery("SELECT MAX(id) FROM User", Integer.class);
        Integer maxId = query.uniqueResult();
        return maxId != null ? maxId : 0; // Return 0 if no users exist
    }

    @Override
    public List<User> getAll(Session session) {
        Query<User> query = session.createQuery("FROM User ", User.class);
        return query.list();
    }

    @Override
    public User getUser(String name, Session session) {
        Query<User> query = session.createQuery("FROM User WHERE userName = :userName", User.class);
        query.setParameter("userName", name);
        return query.uniqueResult();
    }

    @Override
    public boolean update(User user, Session session) {
        session.update(user);
        return true;
    }

    @Override
    public boolean delete(String userName, Session session) {
        Query<User> query = session.createQuery("FROM User WHERE userName = :userName", User.class);
        query.setParameter("userName", userName);
        User user = query.uniqueResult();
        if (user != null) {
            session.delete(user);
            return true;
        } else {
            // User not found
            return false;
        }
    }
}
