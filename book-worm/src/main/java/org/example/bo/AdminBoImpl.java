package org.example.bo;

import org.example.dao.AdminDao;
import org.example.dao.AdminDaoImpl;
import org.example.dto.AdminDto;
import org.example.entity.Admin;

import java.sql.SQLException;

public class AdminBoImpl implements AdminBo{
    private AdminDao adminDao = new AdminDaoImpl();
    @Override
    public boolean saveAdmin(AdminDto dto) throws SQLException {
        return adminDao.save(new Admin(dto.getId(), dto.getName(), dto.getPassword(), dto.getEmail()));
    }

    @Override
    public boolean isAdminTableEmpty() throws SQLException {
        try {
            return adminDao.count() == 0; // Assuming adminDao provides a count() method to count admins
        } catch (Exception e) {
            throw new SQLException("Error checking if admin table is empty", e);
        }
    }

    @Override
    public int getNextAdminId() throws SQLException {
        try {
            int maxId = adminDao.getMaxAdminId();
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next admin ID", e);
        }
    }
}
