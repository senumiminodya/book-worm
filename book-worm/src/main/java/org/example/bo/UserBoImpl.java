package org.example.bo;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.dto.UserDto;
import org.example.entity.User;

import java.sql.SQLException;

public class UserBoImpl implements UserBo{
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean saveUser(UserDto dto) throws SQLException {
        return userDao.save(new User(dto.getId(), dto.getUserName(), dto.getPassword(), dto.getEmail(), dto.getPhoneNo()));
    }
    @Override
    public boolean isUserTableEmpty() throws SQLException {
        try {
            return userDao.count() == 0; // Assuming userDao provides a count() method to count users
        } catch (Exception e) {
            throw new SQLException("Error checking if user table is empty", e);
        }
    }

    @Override
    public int getNextUserId() throws SQLException {
        try {
            int maxId = userDao.getMaxUserId();
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next user ID", e);
        }
    }
}
