package org.example.dao;

import org.example.entity.Book;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BookDaoImpl implements BookDao{
    @Override
    public boolean save(Book book, Session session) {
        Object save =  session.save(book);
        return save !=null;
    }

    @Override
    public long count(Session session) {
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Book", Long.class);
        return query.uniqueResult();
    }

    @Override
    public int getMaxBookId(Session session) {
        Query<Integer> query = session.createQuery("SELECT MAX(id) FROM Book", Integer.class);
        Integer maxId = query.uniqueResult();
        return maxId != null ? maxId : 0; // Return 0 if no users exist
    }

    @Override
    public boolean delete(String bookTitle, Session session) {
        Query<Book> query = session.createQuery("FROM Book WHERE title = :title", Book.class);
        query.setParameter("title", bookTitle);
        Book book = query.uniqueResult();
        if (book != null) {
            session.delete(book);
            return true;
        } else {
            // Book not found
            return false;
        }
    }

    @Override
    public boolean update(Book book, Session session) {
        session.update(book);
        return true;
    }

    @Override
    public Book getBook(String bookTitle, Session session) {
        Query<Book> query = session.createQuery("FROM Book WHERE title = :title", Book.class);
        query.setParameter("title", bookTitle);
        return query.uniqueResult();
    }

    @Override
    public List<Book> getAll(Session session) {
        Query<Book> query = session.createQuery("FROM Book ", Book.class);
        return query.list();
    }

    public void changeAvailability(Book book, Session session) {
        session.createQuery("UPDATE Book SET availability = :availability WHERE id = :id").
                setParameter("availability", book.getAvailability()).
                setParameter("id", book.getId()).executeUpdate();
    }

    public void changeAvailability(List<Book> list,Session session){
        for (Book book : list) {
            changeAvailability(book, session);
        }
    }


}
