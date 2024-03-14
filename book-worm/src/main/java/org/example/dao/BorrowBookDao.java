package org.example.dao;

import org.example.entity.BorrowBook;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public interface BorrowBookDao {
    List<BorrowBook> getAll(Session session);

    void borrowBook(ArrayList<BorrowBook> borrowBooks, Session session);
    List<Object[]> getNotReturnedBooksByUserId(int userId, Session session);

    void returnBook(int recordId, Session session);
    List<Object[]> loadHistory(int id , Session session);
}
