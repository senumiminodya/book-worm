package org.example.dao;

import org.example.entity.User;
import org.hibernate.Session;

import java.sql.SQLException;

public interface UserDao {
    boolean save(User user, Session session) throws SQLException;
    public long count(Session session);
    public int getMaxUserId(Session session);
}
