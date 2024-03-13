package org.example.bo;

import org.example.config.FactoryConfiguration;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.dto.UserDto;
import org.example.entity.Admin;
import org.example.entity.Branch;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserBoImpl implements UserBo{
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean saveUser(UserDto dto) throws SQLException {
        //return userDao.save(new User(dto.getId(), dto.getUserName(), dto.getPassword(), dto.getEmail(), dto.getPhoneNo()));
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean save = userDao.save(new User(dto.getId(), dto.getUserName(), dto.getPassword(), dto.getEmail(), dto.getPhoneNo()), session);
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
    public boolean isUserTableEmpty() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return userDao.count(session) == 0; // Assuming userDao provides a count() method to count users
        } catch (Exception e) {
            throw new SQLException("Error checking if user table is empty", e);
        }finally {
            session.close();
        }
    }

    @Override
    public int getNextUserId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            int maxId = userDao.getMaxUserId(session);
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next user ID", e);
        }finally {
            session.close();
        }
    }

    public List<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return userDao.getAll(session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUsers(String name) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return userDao.getUser(name, session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean update(UserDto dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean update = userDao.update(new User(dto.getId(), dto.getUserName(), dto.getEmail(), dto.getPhoneNo(), dto.getPassword()), session);
            transaction.commit();
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean deleteUser(String userName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean delete = userDao.delete(userName, session);
            transaction.commit();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }
}
