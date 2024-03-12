package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BranchDaoImpl implements BranchDao{
    @Override
    public boolean save(Branch branch, Session session) {
        Object save =  session.save(branch);
        return save !=null;
    }

    @Override
    public long count(Session session) {
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Branch", Long.class);
        return query.uniqueResult();
    }

    @Override
    public int getMaxUserId(Session session) {
        Query<Integer> query = session.createQuery("SELECT MAX(id) FROM Branch", Integer.class);
        Integer maxId = query.uniqueResult();
        return maxId != null ? maxId : 0; // Return 0 if no users exist
    }

    @Override
    public boolean delete(String branchName, Session session) {
        Query<Branch> query = session.createQuery("FROM Branch WHERE branchName = :branchName", Branch.class);
        query.setParameter("branchName", branchName);
        Branch branch = query.uniqueResult();
        if (branch != null) {
            session.delete(branch);
            return true;
        } else {
            // Branch not found
            return false;
        }
    }

    @Override
    public boolean update(Branch branch, Session session) {
        session.update(branch);
        return true;
    }

    @Override
    public Branch getBranch(String branchName, Session session) {
        Query<Branch> query = session.createQuery("FROM Branch WHERE branchName = :branchName", Branch.class);
        query.setParameter("branchName", branchName);
        return query.uniqueResult();
    }

    @Override
    public List<Branch> getAll(Session session) {
        Query<Branch> query = session.createQuery("FROM Branch ", Branch.class);
        return query.list();
    }

}
