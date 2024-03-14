package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.bo.BorrowBookBo;
import org.example.bo.BorrowBookBoImpl;
import org.example.tm.HistoryTM;

import java.time.LocalDate;
import java.util.List;

public class ViewHistoryController {

    @FXML
    private TableColumn<HistoryTM, String> colAuthor;

    @FXML
    private TableColumn<HistoryTM, LocalDate> colBorrowedDate;

    @FXML
    private TableColumn<HistoryTM, Boolean> colGenre;

    @FXML
    private TableColumn<HistoryTM,Integer> colId;

    @FXML
    private TableColumn<HistoryTM, LocalDate> colReturnedDate;

    @FXML
    private TableColumn<HistoryTM, String> colTitle;

    @FXML
    private TableView<HistoryTM> tblBorrowBooks;

    private BorrowBookBo borrowBookBo = new BorrowBookBoImpl();

    public void initialize(){
        setCellValueFactories();
        setTableData();
    }

    private void setTableData() {
        List<HistoryTM> historyTMS = borrowBookBo.loadHistory();
        tblBorrowBooks.setItems(FXCollections.observableList(historyTMS));
    }

    private void setCellValueFactories() {
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }

}
