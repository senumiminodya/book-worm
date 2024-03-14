package org.example.bo;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.example.config.FactoryConfiguration;
import org.example.dao.BookDao;
import org.example.dao.BookDaoImpl;
import org.example.dao.BorrowBookDao;
import org.example.dao.BorrowBookDaoImpl;
import org.example.entity.Book;
import org.example.entity.BorrowBook;
import org.example.entity.User;
import org.example.indb.DB;
import org.example.tm.BorrowBookTm;
import org.example.tm.HistoryTM;
import org.example.tm.NotReturnedTM;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowBookBoImpl implements BorrowBookBo{

    BorrowBookDao borrowBookDao = new BorrowBookDaoImpl();
    BookDao bookDao = new BookDaoImpl();

    @Override
    public List<BorrowBook> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return borrowBookDao.getAll(session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public void save(BorrowBook borrowBook) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            //borrowBookDao.save(borrowBook, session);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void borrowBook(ObservableList<BorrowBookTm> items) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<BorrowBook> borrowBooks = new ArrayList<>();
        ArrayList<Book> bookList = new ArrayList<>();
        User user = new User();
        user.setId(DB.loggedUserId);
        session.detach(user);
        for (BorrowBookTm item : items) {
            BorrowBook borrowBook = new BorrowBook();
            Book book = new Book();
            bookList.add(book);
            book.setId(item.getId());
            book.setAvailability("Not Available");
            borrowBook.setBook(book);
            session.detach(book);
            borrowBook.setGenre(item.getGenre());
            borrowBook.setAuthor(item.getAuthor());
            borrowBook.setUser(user);
            borrowBook.setBorrowDate(Date.valueOf(item.getBorrowDate()));
            borrowBook.setDueDate(Date.valueOf(item.getDueDate()));
            borrowBooks.add(borrowBook);
        }
        try {
            borrowBookDao.borrowBook(borrowBooks, session);
            bookDao.changeAvailability(bookList, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }



    public List<NotReturnedTM> getNotReturnedBooksByUserId() {
        List<NotReturnedTM> list = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Object[]> notReturnedBooksByUserId = borrowBookDao.getNotReturnedBooksByUserId(DB.loggedUserId, session);
            for (Object[] objects : notReturnedBooksByUserId) {
                NotReturnedTM notReturnedTM = new NotReturnedTM();
                notReturnedTM.setRecordId((int) objects[0]);
                notReturnedTM.setTitle((String) objects[1]);
                notReturnedTM.setAuthor((String) objects[2]);
                notReturnedTM.setGenre((String) objects[3]);
                notReturnedTM.setBorrowDate(((Timestamp) objects[4]).toLocalDateTime().toLocalDate());
                notReturnedTM.setBookId((int) objects[5]);
                notReturnedTM.setButton(new Button("Return"));
                list.add(notReturnedTM);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return list;
    }

    @Override
    public void returnBook(NotReturnedTM notReturnedTM) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            borrowBookDao.returnBook(notReturnedTM.getRecordId(), session);
            Book book = new Book();
            book.setId(notReturnedTM.getBookId());
            book.setAvailability("Available");
            session.detach(book);
            bookDao.changeAvailability(book, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    public List<HistoryTM> loadHistory() {
        Session session = FactoryConfiguration.getInstance().getSession();
        ArrayList<HistoryTM> historyTMS = new ArrayList<>();
        try {
            List<Object[]> objects = borrowBookDao.loadHistory(DB.loggedUserId, session);
            for (Object[] object : objects) {
                HistoryTM historyTM = new HistoryTM();
                historyTM.setBookId((int) object[0]);
                historyTM.setTitle((String) object[1]);
                historyTM.setAuthor((String) object[2]);
                historyTM.setIsReturned((boolean) object[3]);
                historyTM.setBorrowDate(((Timestamp) object[4]).toLocalDateTime().toLocalDate());
                historyTM.setDueDate(((Timestamp) object[5]).toLocalDateTime().toLocalDate());
                historyTMS.add(historyTM);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
        return historyTMS;
    }
}
