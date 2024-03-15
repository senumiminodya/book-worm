package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.example.bo.BookBo;
import org.example.bo.BookBoImpl;
import org.example.bo.BranchBo;
import org.example.bo.BranchBoImpl;
import org.example.cm.BranchCm;
import org.example.dto.BookDto;
import org.example.dto.BranchDto;
import org.example.entity.Book;
import org.example.entity.Branch;
import org.example.tm.BookTm;
import org.example.tm.BranchTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageController {
    @FXML
    private AnchorPane booksAnchorpane;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<BranchCm> cmbBranches;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colBranch;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Label lblAvailability;

    @FXML
    private TableView<BookTm> tblBooks;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtGenre;

    @FXML
    private TextField txtTitle;

    BookBo bookBo = new BookBoImpl();
    BranchBo branchBo = new BranchBoImpl();

    public void initialize() throws SQLException {
        int nextBookId;
        if (bookBo.isBookTableEmpty()) {
            nextBookId = 1; // Set ID to 1 if no user is saved in the database
        } else {
            nextBookId = bookBo.getNextBookId(); // Get the next ID from the database
        }
        txtBookId.setText(String.valueOf(nextBookId));
        cmbBranches.setConverter(new StringConverter<BranchCm>() {
            @Override
            public String toString(BranchCm branchCm) {
                return branchCm==null ? "" : branchCm.getName();
            }

            @Override
            public BranchCm fromString(String s) {
                return null;
            }
        });
        setComboBox();
        setCellvalueFactory();
        tableListener();
        loadAllBooks();
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        txtBookId.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtGenre.clear();
        lblAvailability.setText(null);
        cmbBranches.getSelectionModel().clearSelection();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        String title = txtTitle.getText();
        if (!title.isEmpty()) {
            if (bookBo.deleteBook(title)) {
                new Alert(Alert.AlertType.INFORMATION, "Book deleted successfully!").show();
                txtBookId.clear();
                txtTitle.clear();
                txtAuthor.clear();
                txtGenre.clear();
                lblAvailability.setText(null);
                cmbBranches.getSelectionModel().clearSelection();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error deleting book from the database").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter the book title to delete").show();
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtBookId.getText());
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String availability = lblAvailability.getText();
        String branch = cmbBranches.getValue().getName();
        int branchId = cmbBranches.getValue().getId();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || availability.isEmpty() || branch.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtTitle.requestFocus();
            return;
        } else {
            BookDto bookDto = new BookDto(id, title, author, genre, availability, branch,branchId);
            if (bookBo.saveBook(bookDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Book saved successfully!").show();
                txtBookId.clear();
                txtTitle.clear();
                txtAuthor.clear();
                txtGenre.clear();
                lblAvailability.setText(null);
                cmbBranches.getSelectionModel().clearSelection();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error saving book to the database").show();
            }
        }
    }

    @FXML
    void txtTitleOnAction(ActionEvent event) {
        fetchAndDisplayBookDetails();
    }
    private void fetchAndDisplayBookDetails() {
        String title = txtTitle.getText().trim(); // Trim the input to remove leading/trailing spaces
        if (!title.isEmpty()) { // Check if the input is not empty
            Book book = bookBo.getBooks(title);
            if (book != null) {
                txtBookId.setText(String.valueOf(book.getId()));
                txtAuthor.setText(book.getAuthor());
                txtGenre.setText(book.getGenre());
                lblAvailability.setText(book.getAvailability());
                //cmbBranches.setItems(book.getBranchName());
            } else {
                // Branch not found
                new Alert(Alert.AlertType.ERROR, "Book not found").show();
            }
        } else {
            // Show error if no branch name is entered
            new Alert(Alert.AlertType.ERROR, "Please enter the book title to search").show();
        }
    }

    private void loadAllBooks() {
        ObservableList<BookTm> observableList = FXCollections.observableArrayList();
        List<Book> bookList = bookBo.getAll(); // Assuming you have a branchesBO object
        for (Book book : bookList) {
            // Create BranchTm object using book details and add it to the observable list
            observableList.add(new BookTm(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getAvailability(),""));
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
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branchName"));
    }
    private void tableListener() {
        tblBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BookTm selectedBook = (BookTm) newValue;
                txtBookId.setText(String.valueOf(selectedBook.getId()));
                txtTitle.setText(selectedBook.getTitle());
                txtAuthor.setText(selectedBook.getAuthor());
                txtGenre.setText(selectedBook.getGenre());
                lblAvailability.setText(selectedBook.getAvailability());
                //cmbBranches.setValue(branchName);
            }
        });
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtBookId.getText());
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String availability = lblAvailability.getText();
        String branch = cmbBranches.getValue().getName();
        int id1 = cmbBranches.getValue().getId();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || availability.isEmpty() || branch.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtTitle.requestFocus();
            return;
        } else {
            BookDto bookDto = new BookDto(id, title, author, genre, availability, branch,id1);
            if (bookBo.update(bookDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Book updated successfully!").show();
                txtBookId.clear();
                txtTitle.clear();
                txtAuthor.clear();
                txtGenre.clear();
                lblAvailability.setText(null);
                cmbBranches.getSelectionModel().clearSelection();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error updating book in the database").show();
            }
        }
    }
    public void setComboBox() {
        List<Branch> allBranches = branchBo.getAll();
        System.out.println(allBranches.size());
        ArrayList<BranchCm> objects = new ArrayList<>();
        for (Branch branch : allBranches) {
            BranchCm branchCm = new BranchCm();
            branchCm.setId(branch.getId());
            branchCm.setName(branch.getBranchName());
            branchCm.setAddress(branch.getAddress());
            branchCm.setContactNo(branch.getContactNo());
            objects.add(branchCm);
        }
        cmbBranches.setItems(FXCollections.observableArrayList(objects));
    }
}
