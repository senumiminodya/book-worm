package org.example.dao;

import org.example.entity.Admin;
import org.example.entity.User;

import java.sql.SQLException;

public interface AdminDao {
    boolean save(Admin admin) throws SQLException;
    public long count();
    public int getMaxAdminId();
}
