package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginFormController {
    @FXML
    private Button adminLoginBtn;

    @FXML
    private Button adminSignupBtn;

    @FXML
    void adminLoginBtnOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)adminLoginBtn.getScene().getWindow();
        window.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/adminDashboard.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
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
