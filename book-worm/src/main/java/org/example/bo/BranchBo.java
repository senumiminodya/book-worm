package org.example.bo;

import org.example.dto.BranchDto;
import org.example.entity.Branch;

import java.sql.SQLException;
import java.util.List;

public interface BranchBo {
    boolean saveBranch(BranchDto dto);

    boolean isBranchTableEmpty() throws SQLException;

    int getNextUserId() throws SQLException;

    boolean deleteBranch(String branchName);

    public boolean update(BranchDto dto);
    public Branch getBranches(String branchName);
    public List<Branch> getAll();

}
