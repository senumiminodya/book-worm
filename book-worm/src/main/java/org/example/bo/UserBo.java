package org.example.bo;

import org.example.dto.UserDto;

import java.sql.SQLException;

public interface UserBo {
    boolean saveUser(UserDto dto) throws SQLException;
}
