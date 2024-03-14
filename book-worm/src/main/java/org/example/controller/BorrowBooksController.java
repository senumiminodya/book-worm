package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.bo.BookBo;
import org.example.bo.BookBoImpl;
import org.example.bo.BorrowBookBo;
import org.example.bo.BorrowBookBoImpl;
import org.example.entity.Book;
import org.example.tm.BorrowBookTm;

import java.io.IOException;
import java.time.LocalDate;

public class BorrowBooksController {
    @FXML
    private JFXButton btnAddCart;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableColumn<BorrowBookTm, String> colAuthor;

    @FXML
    private TableColumn<BorrowBookTm, Button> colBorrow;

    @FXML
    private TableColumn<BorrowBookTm, String> colGenre;

    @FXML
    private TableColumn<BorrowBookTm, String> colTitle;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblGenre;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblTodayDate;

    @FXML
    private JFXButton returnBooksBtn;

    @FXML
    private TableView<BorrowBookTm> tblBorrowBooks;

    @FXML
    private TextField txtTitle;

    private int bookId;

    BookBo bookBo = new BookBoImpl();
    BorrowBookBo borrowBookBo = new BorrowBookBoImpl();


    @FXML
    void addCartBtnOnAction(ActionEvent event) {


        for (BorrowBookTm item : tblBorrowBooks.getItems()) {
            if (item.getId() == bookId || bookId == 0) {
                return;
            }
        }
        //loadAllBooks();
        System.out.println("add ");
        BorrowBookTm borrowBookTm = new BorrowBookTm();
        borrowBookTm.setId(bookId);
        borrowBookTm.setBookTitle(lblTitle.getText());
        borrowBookTm.setAuthor(lblAuthor.getText());
        borrowBookTm.setGenre(lblGenre.getText());
        borrowBookTm.setBorrowDate(LocalDate.now());
        borrowBookTm.setDueDate(LocalDate.now().plusDays(14));

        Button remove = new Button("Remove");
        remove.setOnAction((e)->{
            tblBorrowBooks.getItems().remove(borrowBookTm);
        });
        borrowBookTm.setButton(remove);
        tblBorrowBooks.getItems().add(borrowBookTm);
        bookId=0;
        clearLabels();
    }

    public void initialize(){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBorrow.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {
        String title = txtTitle.getText().trim(); // Trim the input to remove leading/trailing spaces
        if (!title.isEmpty()) { // Check if the input is not empty
            Book book = bookBo.getBooks(title);
            if (book != null) {
                bookId = book.getId();
                System.out.println(bookId);
                lblTitle.setText(book.getTitle());
                lblAuthor.setText(book.getAuthor());
                lblGenre.setText(book.getGenre());
                lblTodayDate.setText(String.valueOf(LocalDate.now()));
                lblDueDate.setText(String.valueOf(LocalDate.now().plusDays(14)));
            } else {
                // Book not found
                new Alert(Alert.AlertType.ERROR, "Book not found").show();
                bookId=0;
            }
        } else {
            // Show error if no book name is entered
            new Alert(Alert.AlertType.ERROR, "Please enter the book title to search").show();
            bookId=0;
        }
    }

    public void btnBorrowOnAction(ActionEvent actionEvent) {
        ObservableList<BorrowBookTm> items = tblBorrowBooks.getItems();
        try {
            borrowBookBo.borrowBook(items);
            new Alert(Alert.AlertType.INFORMATION, "Book borrowed successfully").show();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        //borrowBookBo.borrowBook(bookId);
    }

    void clearLabels(){
        lblTitle.setText("");
        lblAuthor.setText("");
        lblGenre.setText("");
        lblTodayDate.setText("");
        lblDueDate.setText("");
        txtTitle.clear();
    }
}
