package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.Admin;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao{

    @Override
    public boolean save(Admin admin, Session session) throws SQLException {
        Object save =  session.save(admin);
        return save !=null;
    }

    public boolean validateAdmin(String userName, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Admin> query = session.createQuery("FROM Admin WHERE userName = :username AND password = :password", Admin.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);
        Admin admin = query.uniqueResult();
        return admin != null;
    }

    @Override
    public long count(Session session) {
        //Session session = FactoryConfiguration.getInstance().getSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Admin", Long.class);
        return query.uniqueResult();
    }

    @Override
    public int getMaxAdminId(Session session) {
        //Session session = FactoryConfiguration.getInstance().getSession();
        Query<Integer> query = session.createQuery("SELECT MAX(id) FROM Admin", Integer.class);
        Integer maxId = query.uniqueResult();
        return maxId != null ? maxId : 0; // Return 0 if no users exist
    }
}
