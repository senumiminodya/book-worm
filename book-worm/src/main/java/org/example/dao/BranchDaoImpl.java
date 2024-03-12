package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BranchDaoImpl implements BranchDao{
    @Override
    public boolean save(Branch branch) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object save =  session.save(branch);
        transaction.commit();
        session.close();
        return save !=null;
    }

    @Override
    public long count() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Branch", Long.class);
        return query.uniqueResult();
    }

    @Override
    public int getMaxUserId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Integer> query = session.createQuery("SELECT MAX(id) FROM Branch", Integer.class);
        Integer maxId = query.uniqueResult();
        return maxId != null ? maxId : 0; // Return 0 if no users exist
    }

    @Override
    public void delete(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Branch branch = getDetail(id);
        session.delete(branch);
        transaction.commit();
        session.close();
    }

    @Override
    public Branch getDetail(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Branch branch = session.get(Branch.class, id);
        transaction.commit();
        session.close();
        return branch;
    }

    @Override
    public List<Branch> getAll() {
        return null;
    }

}
