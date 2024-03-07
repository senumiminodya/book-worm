package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserSignUpFormController {
    @FXML
    private Button userLogInBtn;

    @FXML
    private Button userSignUpBtn;

    @FXML
    void userLogInBtnOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)userLogInBtn.getScene().getWindow();
        window.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/userLoginForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("User Dashboard");
        stage.show();
    }

    @FXML
    void userSignUpBtnOnAction(ActionEvent event) {

    }
}
