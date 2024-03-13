package org.example.dao;

import org.example.entity.Book;
import org.example.entity.Branch;
import org.hibernate.Session;

import java.util.List;

public interface BookDao {
    boolean save(Book book, Session session);

    long count(Session session);

    int getMaxBookId(Session session);

    public boolean delete(String bookTitle, Session session);
    public boolean update(Book book, Session session);
    public Book getBook(String bookTitle, Session session);
    public List<Book> getAll(Session session);
}
