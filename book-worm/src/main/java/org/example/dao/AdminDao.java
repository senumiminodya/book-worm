package org.example.dao;

import org.example.entity.Admin;
import org.example.entity.User;
import org.hibernate.Session;

import java.sql.SQLException;

public interface AdminDao {
    boolean save(Admin admin, Session session) throws SQLException;
    public long count(Session session);
    public int getMaxAdminId(Session session);
}
