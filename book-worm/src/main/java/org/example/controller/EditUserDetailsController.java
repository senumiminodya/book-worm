package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.bo.UserBoImpl;
import org.example.dto.BookDto;
import org.example.dto.UserDto;
import org.example.entity.User;

public class EditUserDetailsController {
    @FXML
    private JFXButton resetBtn;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNo;
    UserBoImpl userBoImpl = new UserBoImpl();
    public void initialize() {
        for (User user : userBoImpl.getAll()) {
            txtId.setText(String.valueOf(user.getId()));
            txtName.setText(user.getUserName());
            txtEmail.setText(user.getEmail());
            txtPassword.setText(user.getPassword());
            txtPhoneNo.setText(user.getPhoneNo());
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String phoneNo = txtPhoneNo.getText();
        userBoImpl.update(new UserDto(id, name, email, password, phoneNo));

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNo.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtName.requestFocus();
            return;
        } else {
            UserDto userDto = new UserDto(id, name, email, password, phoneNo);
            if (userBoImpl.update(userDto)) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                txtId.clear();
                txtName.clear();
                txtEmail.clear();
                txtPassword.clear();
                txtPhoneNo.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error updating user in the database").show();
            }
        }
    }
}
