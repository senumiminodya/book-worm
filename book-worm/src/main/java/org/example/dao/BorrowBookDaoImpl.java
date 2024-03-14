package org.example.dao;

import org.example.entity.BorrowBook;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BorrowBookDaoImpl implements BorrowBookDao{
    @Override
    public List<BorrowBook> getAll(Session session) {
        Query<BorrowBook> query = session.createQuery("FROM BorrowBook ", BorrowBook.class);
        return query.list();
    }

    @Override
    public void borrowBook(ArrayList<BorrowBook> borrowBooks, Session session) {
        for (BorrowBook borrowBook : borrowBooks) {
            session.persist(borrowBook);
        }
    }

    public List<Object[]> getNotReturnedBooksByUserId(int userId, Session session) {
        System.out.println("User Id : "+userId);
        return session.createQuery("select b.id , b.book.title , b.author , b.genre , b.borrowDate , b.book.id FROM BorrowBook b WHERE b.user.id = :userId AND b.isReturned is false ",Object[].class)
                .setParameter("userId", userId).list();
    }

    @Override
    public void returnBook(int recordId, Session session) {
        session.createQuery("update BorrowBook b set b.isReturned = true where b.id = :recordId")
                .setParameter("recordId", recordId).executeUpdate();
    }


    public List<Object[]> loadHistory(int id , Session session){
        return session.createQuery("select b.book.id , b.book.title , b.book.author , b.isReturned , b.borrowDate" +
                ", b.dueDate from BorrowBook b where b.user.id = :id",Object[].class).setParameter("id", id).list();
    }
}
