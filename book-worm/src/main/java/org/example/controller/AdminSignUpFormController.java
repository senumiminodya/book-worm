package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.bo.AdminBo;
import org.example.bo.AdminBoImpl;
import org.example.dto.AdminDto;


import java.io.IOException;
import java.sql.SQLException;

public class AdminSignUpFormController {
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;
    @FXML
    private Button adminLoginBtn;

    @FXML
    private Button adminSignUpBtn;

    public void initialize() {
        AdminBo adminBo = new AdminBoImpl();

        try {
            int nextAdminId;
            if (adminBo.isAdminTableEmpty()) {
                nextAdminId = 1; // Set ID to 1 if no user is saved in the database
            } else {
                nextAdminId = adminBo.getNextAdminId(); // Get the next ID from the database
            }
            txtId.setText(String.valueOf(nextAdminId));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error initializing admin ID").show();
        }
    }

    @FXML
    void adminLoginBtnOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)adminLoginBtn.getScene().getWindow();
        window.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/adminLoginForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin Log In");
        stage.show();
    }

    @FXML
    void adminSignUpBtnOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        if (userName.isEmpty() || password.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtUserName.requestFocus();
            return;
        } else {
            AdminBo adminBo = new AdminBoImpl();
            AdminDto adminDto = new AdminDto(id, userName, password, email);
            try {
                if (adminBo.saveAdmin(adminDto)) {
                    new Alert(Alert.AlertType.INFORMATION, "You are saved successfully! Please Log In...").show();
                    txtId.clear();
                    txtUserName.clear();
                    txtPassword.clear();
                    txtEmail.clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error saving user to the database").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
