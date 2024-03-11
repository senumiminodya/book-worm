package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import org.example.dao.UserDaoImpl;

import java.io.IOException;

public class UserLoginFormController {
    @FXML
    private Button userLoginBtn;

    @FXML
    private Button userSignUpBtn;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;


    @FXML
    void userLoginBtnOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtUserName.requestFocus();
            return;
        }

        // Check if the user exists in the database
        UserDaoImpl userDAO = new UserDaoImpl();
        if (userDAO.validateUser(userName, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login successful!");

            // Event handler for the default "OK" button
            alert.setOnHidden(e -> {
                try {
                    // Load the dashboard content
                    Parent dashboard = FXMLLoader.load(getClass().getResource("/view/userDashboard.fxml"));
                    Scene dashboardScene = new Scene(dashboard);

                    // Get the current stage (login window)
                    Stage currentStage = (Stage) userLoginBtn.getScene().getWindow();
                    currentStage.setTitle("User Dashboard");
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

        /*Stage window = (Stage)userLoginBtn.getScene().getWindow();
        window.close();
        Parent load = FXMLLoader.load(getClass().getResource("/view/userDashboard.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("User Dashboard");
        stage.show();*/
    }

    @FXML
    void userSignUpBtnOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)userSignUpBtn.getScene().getWindow();
        window.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/userSignUpForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("User Sign Up");

        stage.show();
    }
}
