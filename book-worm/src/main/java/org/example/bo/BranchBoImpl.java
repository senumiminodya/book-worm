package org.example.bo;

import org.example.config.FactoryConfiguration;
import org.example.dao.BranchDao;
import org.example.dao.BranchDaoImpl;
import org.example.dto.BranchDto;
import org.example.entity.Admin;
import org.example.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchBoImpl implements BranchBo{
    BranchDao branchDao = new BranchDaoImpl();
    @Override
    public boolean saveBranch(BranchDto dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean save = branchDao.save(new Branch(dto.getId(), dto.getName(), dto.getAddress(), dto.getContactNo()), session);
            transaction.commit();
            return save;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean isBranchTableEmpty() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return branchDao.count(session) == 0; // Assuming userDao provides a count() method to count users
        } catch (Exception e) {
            throw new SQLException("Error checking if user table is empty", e);
        }
    }

    @Override
    public int getNextUserId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            int maxId = branchDao.getMaxUserId(session);
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next user ID", e);
        }
    }

    @Override
    public boolean deleteBranch(String branchName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean delete = branchDao.delete(branchName, session);
            transaction.commit();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(BranchDto dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean update = branchDao.update(new Branch(dto.getId(), dto.getName(), dto.getAddress(), dto.getContactNo()), session);
            transaction.commit();
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Branch getBranches(String branchName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return branchDao.getBranch(branchName, session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Branch> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return branchDao.getAll(session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }



}
