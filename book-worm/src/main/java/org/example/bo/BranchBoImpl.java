package org.example.bo;

import org.example.dao.BranchDao;
import org.example.dao.BranchDaoImpl;
import org.example.dto.BranchDto;
import org.example.entity.Branch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchBoImpl implements BranchBo{
    BranchDao branchDao = new BranchDaoImpl();
    @Override
    public boolean saveBranch(BranchDto dto) {
        return branchDao.save(new Branch(dto.getId(), dto.getName(), dto.getAddress(), dto.getContactNo()));
    }

    @Override
    public boolean isBranchTableEmpty() throws SQLException {
        try {
            return branchDao.count() == 0; // Assuming userDao provides a count() method to count users
        } catch (Exception e) {
            throw new SQLException("Error checking if user table is empty", e);
        }
    }

    @Override
    public int getNextUserId() throws SQLException {
        try {
            int maxId = branchDao.getMaxUserId();
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next user ID", e);
        }
    }

    @Override
    public void deleteBranch(int id) {
        branchDao.delete(id);
    }


}
