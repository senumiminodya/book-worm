package org.example.bo;

import javafx.collections.ObservableList;
import org.example.entity.BorrowBook;
import org.example.tm.BorrowBookTm;
import org.example.tm.HistoryTM;
import org.example.tm.NotReturnedTM;

import java.util.List;

public interface BorrowBookBo {
    List<BorrowBook> getAll();
    void save(BorrowBook borrowBook);

    void borrowBook(ObservableList<BorrowBookTm> items);
    List<NotReturnedTM> getNotReturnedBooksByUserId();
    List<HistoryTM> loadHistory();

    void returnBook(NotReturnedTM notReturnedTM);
}
