package org.example.dao;

import org.example.entity.Branch;

import java.util.List;

public interface BranchDao {
    boolean save(Branch branch);

    long count();

    int getMaxUserId();

    void delete(int id);
    Branch getDetail(int id);

    List<Branch> getAll();

}
