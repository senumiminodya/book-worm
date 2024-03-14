package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.bo.BorrowBookBo;
import org.example.bo.BorrowBookBoImpl;
import org.example.tm.NotReturnedTM;

import java.time.LocalDate;
import java.util.List;

public class UserReturnBooksController {

    @FXML
    private TableColumn<NotReturnedTM, String> colAuthor;

    @FXML
    private TableColumn<NotReturnedTM, LocalDate> colBorrowDate;

    @FXML
    private TableColumn<NotReturnedTM, String> colGenre;

    @FXML
    private TableColumn<NotReturnedTM, Button> colReturn;

    @FXML
    private TableColumn<NotReturnedTM, String> colTitle;

    @FXML
    private TableView<NotReturnedTM> tblReturnBooks;

    BorrowBookBo borrowBookBo = new BorrowBookBoImpl();

    public void initialize(){
        setCellValueFactories();
        setTableData();
    }

    private void setCellValueFactories() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturn.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    private void setTableData(){
        List<NotReturnedTM> notReturnedBooksByUserId = borrowBookBo.getNotReturnedBooksByUserId();
        for (NotReturnedTM notReturnedTM : notReturnedBooksByUserId) {
            notReturnedTM.getButton().setOnAction(e->{
                returnBook(notReturnedTM);
            });
        }
        tblReturnBooks.setItems(FXCollections.observableArrayList(notReturnedBooksByUserId));

    }

    private void returnBook(NotReturnedTM notReturnedTM){
        borrowBookBo.returnBook(notReturnedTM);
        setTableData();

    }

}
