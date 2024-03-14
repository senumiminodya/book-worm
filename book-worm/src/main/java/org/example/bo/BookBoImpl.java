package org.example.bo;

import org.example.config.FactoryConfiguration;
import org.example.dao.BookDao;
import org.example.dao.BookDaoImpl;
import org.example.dto.BookDto;
import org.example.entity.Book;
import org.example.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class BookBoImpl implements BookBo{
    BookDao bookDao = new BookDaoImpl();
    @Override
    public boolean saveBook(BookDto dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Book book = new Book(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getGenre(), dto.getAvailability());
            Branch branch = new Branch();
            branch.setId(dto.getBranchId());
            book.setBranch(branch);
            session.detach(branch);
            boolean save = bookDao.save(book, session);
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
    public boolean isBookTableEmpty() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return bookDao.count(session) == 0; // Assuming userDao provides a count() method to count users
        } catch (Exception e) {
            throw new SQLException("Error checking if user table is empty", e);
        }
    }

    @Override
    public int getNextBookId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            int maxId = bookDao.getMaxBookId(session);
            return maxId + 1;
        } catch (Exception e) {
            throw new SQLException("Error getting next book ID", e);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean deleteBook(String bookTitle) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean delete = bookDao.delete(bookTitle, session);
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
    public boolean update(BookDto dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Book book = new Book(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getGenre(), dto.getAvailability());
            Branch branch = new Branch();
            branch.setId(dto.getBranchId());
            book.setBranch(branch);
            session.detach(branch);
            boolean update = bookDao.update(book, session);
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
    public Book getBooks(String bookTitle) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return bookDao.getBook(bookTitle, session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Book> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return bookDao.getAll(session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }
}
