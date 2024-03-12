package org.example.bo;

import org.example.dto.BranchDto;

import java.sql.SQLException;
import java.util.List;

public interface BranchBo {
    boolean saveBranch(BranchDto dto);

    boolean isBranchTableEmpty() throws SQLException;

    int getNextUserId() throws SQLException;

    void deleteBranch(int id);

}
