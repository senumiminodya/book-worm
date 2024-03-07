package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSignUpFormController {
    @FXML
    private Button adminLoginBtn;

    @FXML
    private Button adminSignUpBtn;

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

    }

}
