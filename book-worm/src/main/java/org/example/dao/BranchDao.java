package org.example.dao;

import org.example.entity.Branch;
import org.hibernate.Session;

import java.util.List;

public interface BranchDao {
    boolean save(Branch branch, Session session);

    long count(Session session);

    int getMaxUserId(Session session);

    public boolean delete(String branchName, Session session);
    public boolean update(Branch branch, Session session);
    public Branch getBranch(String branchName, Session session);
    public List<Branch> getAll(Session session);

}
