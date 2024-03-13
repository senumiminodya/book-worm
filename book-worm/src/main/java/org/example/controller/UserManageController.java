package org.example.controller;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.UserBoImpl;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.entity.Branch;
import org.example.entity.User;
import org.example.tm.UserTm;

import java.sql.SQLException;
import java.util.List;

public class UserManageController {
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private AnchorPane userAnchorpane;

    UserBoImpl userBo = new UserBoImpl();

    public void initialize() throws SQLException {
        int nextUserId;
        if (userBo.isUserTableEmpty()) {
            nextUserId = 1; // Set ID to 1 if no user is saved in the database
        } else {
            nextUserId = userBo.getNextUserId(); // Get the next ID from the database
        }
        txtId.setText(String.valueOf(nextUserId));
        setCellvalueFactory();
        tableListener();
        loadAllUsers();
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtPhoneNo.clear();
        txtPassword.clear();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        String userName = txtName.getText();
        if (!userName.isEmpty()) {
            if (userBo.deleteUser(userName)) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!").show();
                txtId.clear();
                txtName.clear();
                txtEmail.clear();
                txtPhoneNo.clear();
                txtPassword.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error deleting user from the database").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter the user name to delete").show();
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phoneNo = txtPhoneNo.getText();
        String password = txtPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phoneNo.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtName.requestFocus();
            return;
        } else {
            UserDto userDto = new UserDto(id, name, password, email, phoneNo);
            if (userBo.saveUser(userDto)) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
                loadAllUsers();
                txtId.clear();
                txtName.clear();
                txtEmail.clear();
                txtPhoneNo.clear();
                txtPassword.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error saving user to the database").show();
            }
        }
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        fetchAndDisplayBranchDetails();
    }
    private void fetchAndDisplayBranchDetails() {
        String name = txtName.getText().trim(); // Trim the input to remove leading/trailing spaces
        if (!name.isEmpty()) { // Check if the input is not empty
            User user = userBo.getUsers(name);
            if (user != null) {
                txtId.setText(String.valueOf(user.getId()));
                txtName.setText(user.getUserName());
                txtEmail.setText(user.getEmail());
                txtPhoneNo.setText(user.getPhoneNo());
                txtPassword.setText(user.getPassword());
            } else {
                // User not found
                new Alert(Alert.AlertType.ERROR, "User not found").show();
            }
        } else {
            // Show error if no user name is entered
            new Alert(Alert.AlertType.ERROR, "Please enter the user name to search").show();
        }
    }
    @FXML
    void updateBtnOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phoneNo = txtPhoneNo.getText();
        String password = txtPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phoneNo.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtName.requestFocus();
            return;
        } else {
            UserDto userDto = new UserDto(id, name, email, phoneNo, password);
            if (userBo.update(userDto)) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                txtId.clear();
                txtName.clear();
                txtEmail.clear();
                txtPhoneNo.clear();
                txtPassword.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error updating user in the database").show();
            }
        }
    }

    private void loadAllUsers() {
        ObservableList<UserTm> observableList = FXCollections.observableArrayList();
        List<User> userList = userBo.getAll(); // Assuming you have a branchesBO object
        for (User user : userList) {
            // Create BranchTm object using branch details and add it to the observable list
            observableList.add(new UserTm(user.getId(), user.getUserName(), user.getEmail(), user.getPhoneNo(), user.getPassword()));
        }
        // Clear existing items and set the new observable list
        tblUser.getItems().clear();
        tblUser.setItems(observableList);
    }

    private void setCellvalueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }
    private void tableListener() {
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                UserTm selectedBranch = (UserTm) newValue;
                txtId.setText(String.valueOf(selectedBranch.getId()));
                txtName.setText(selectedBranch.getName());
                txtEmail.setText(selectedBranch.getEmail());
                txtPhoneNo.setText(selectedBranch.getPhoneNo());
                txtPassword.setText(selectedBranch.getPassword());
            }
        });
    }

}
