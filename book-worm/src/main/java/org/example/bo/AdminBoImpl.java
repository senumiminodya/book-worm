package org.example.bo;

import org.example.config.FactoryConfiguration;
import org.example.dao.AdminDao;
import org.example.dao.AdminDaoImpl;
import org.example.dto.AdminDto;
import org.example.entity.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class AdminBoImpl implements AdminBo{
    private AdminDao adminDao = new AdminDaoImpl();
    @Override
    public boolean saveAdmin(AdminDto dto) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean save = adminDao.save(new Admin(dto.getId(), dto.getName(), dto.getPassword(), dto.getEmail()), session);
            transaction.commit();
            return save;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean isAdminTableEmpty() throws SQLException {
        /*try {
            return adminDao.count() == 0; // Assuming adminDao provides a count() method to count admins
        } catch (Exception e) {
            throw new SQLException("Error checking if admin table is empty", e);
        }*/
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return adminDao.count(session) == 0; // Assuming adminDao provides a count() method to count admins
        } catch (Exception e) {
            throw new SQLException("Error checking if admin table is empty", e);
        }finally {
            session.close();
        }
    }

    @Override
    public int getNextAdminId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            int maxId = adminDao.getMaxAdminId(session);
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next admin ID", e);
        }
    }
}
