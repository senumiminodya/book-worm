package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.bo.UserBo;
import org.example.bo.UserBoImpl;
import org.example.dto.UserDto;

import java.io.IOException;
import java.sql.SQLException;

public class UserSignUpFormController {
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private JFXButton userLogInBtn;

    @FXML
    private JFXButton userSignUpBtn;

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
        int id = Integer.parseInt(txtId.getText());
        String userName = txtName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String phoneNo = txtPhoneNo.getText();

        if (userName.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNo.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtName.requestFocus();
            return;
        } else {
            UserBo userBO = new UserBoImpl();
            UserDto userDto = new UserDto(id, userName, password, email, phoneNo);
            try {
                if (userBO.saveUser(userDto)) {
                    new Alert(Alert.AlertType.INFORMATION, "You are saved successfully! Please Log In...").show();
                    int preId = Integer.parseInt(txtId.getText());
                    txtId.setText(String.valueOf(preId+1));
                    txtName.clear();
                    txtPassword.clear();
                    txtEmail.clear();
                    txtPhoneNo.clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error saving user to the database").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
