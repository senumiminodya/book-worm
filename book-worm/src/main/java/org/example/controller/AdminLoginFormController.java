package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.dao.AdminDaoImpl;
import org.example.dao.UserDaoImpl;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminLoginFormController {
    @FXML
    private Button adminLoginBtn;

    @FXML
    private Button adminSignupBtn;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void adminLoginBtnOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtUserName.requestFocus();
            return;
        }

        // Check if the admin exists in the database
        AdminDaoImpl adminDao = new AdminDaoImpl();
        if (adminDao.validateAdmin(userName, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login successful!");

            // Event handler for the default "OK" button
            alert.setOnHidden(e -> {
                try {
                    // Load the dashboard content
                    Parent dashboard = FXMLLoader.load(getClass().getResource("/view/adminDashboard.fxml"));
                    Scene dashboardScene = new Scene(dashboard);

                    // Get the current stage (login window)
                    Stage currentStage = (Stage) adminLoginBtn.getScene().getWindow();
                    currentStage.setTitle("Admin Dashboard");
                    currentStage.setScene(dashboardScene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            alert.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();
        }
    }

    @FXML
    void adminSignupBtnOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)adminSignupBtn.getScene().getWindow();
        window.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/adminSignUpForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin Sign Up");
        stage.show();
    }
}
