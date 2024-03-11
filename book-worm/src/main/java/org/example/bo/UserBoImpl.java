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
}
