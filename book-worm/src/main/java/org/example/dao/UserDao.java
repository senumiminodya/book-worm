package org.example.dao;

import org.example.entity.User;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    boolean save(User user, Session session) throws SQLException;
    public long count(Session session);
    public int getMaxUserId(Session session);

    List<User> getAll(Session session);

    User getUser(String name, Session session);

    boolean update(User user, Session session);

    boolean delete(String userName, Session session);
}
