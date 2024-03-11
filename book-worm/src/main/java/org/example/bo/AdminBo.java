package org.example.bo;

import org.example.dto.AdminDto;
import org.example.dto.UserDto;

import java.sql.SQLException;

public interface AdminBo {
    boolean saveAdmin(AdminDto dto) throws SQLException;
    boolean isAdminTableEmpty() throws SQLException;
    int getNextAdminId() throws SQLException;
}
