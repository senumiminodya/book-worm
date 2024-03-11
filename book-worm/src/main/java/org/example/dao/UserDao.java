package org.example.dao;

import org.example.entity.User;

import java.sql.SQLException;

public interface UserDao {
    boolean save(User user) throws SQLException;
    public long count();
    public int getMaxUserId();
}
