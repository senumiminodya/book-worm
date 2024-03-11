package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao{
    @Override
    public boolean save(User user) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object save =  session.save(user);
        transaction.commit();
        session.close();
        return save !=null;
    }
}
