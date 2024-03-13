package org.example.bo;

import org.example.dto.BookDto;
import org.example.dto.BranchDto;
import org.example.entity.Book;
import org.example.entity.Branch;

import java.sql.SQLException;
import java.util.List;

public interface BookBo {
    boolean saveBook(BookDto dto);

    boolean isBookTableEmpty() throws SQLException;

    int getNextBookId() throws SQLException;

    boolean deleteBook(String bookTitle);

    public boolean update(BookDto dto);
    public Book getBooks(String bookTitle);
    public List<Book> getAll();
}
