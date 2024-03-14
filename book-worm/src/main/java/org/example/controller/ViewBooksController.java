package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.bo.BookBo;
import org.example.bo.BookBoImpl;
import org.example.entity.Book;
import org.example.tm.BookTm;

import java.util.List;

public class ViewBooksController {
    @FXML
    private TableColumn<BookTm, String> colAuthor;

    @FXML
    private TableColumn<BookTm, String> colAvailability;

    @FXML
    private TableColumn<BookTm, String > colGenre;

    @FXML
    private TableColumn<BookTm, Integer> colId;

    @FXML
    private TableColumn<BookTm, String> colTitle;

    @FXML
    private TableView<BookTm> tblBooks;

    BookBo bookBo = new BookBoImpl();

    public void initialize() {
        loadAllBooks();
        setCellvalueFactory();
    }

    private void loadAllBooks() {
        ObservableList<BookTm> observableList = FXCollections.observableArrayList();
        List<Book> bookList = bookBo.getAll(); // Assuming you have a branchesBO object
        for (Book book : bookList) {
            // Create BranchTm object using book details and add it to the observable list
            observableList.add(new BookTm(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getAvailability(), ""));
        }
        // Clear existing items and set the new observable list
        tblBooks.getItems().clear();
        tblBooks.setItems(observableList);
    }
    private void setCellvalueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }
}
