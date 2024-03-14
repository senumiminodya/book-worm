package org.example.controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.example.bo.UserBo;
import org.example.bo.UserBoImpl;
import org.example.entity.User;
import javafx.scene.control.Label;

import java.io.IOException;


public class UserDashboardController {
    @FXML
    private JFXButton UserDetailsBtn;

    @FXML
    private JFXButton borrowBooksBtn;

    @FXML
    private JFXButton returnBooksBtn;

    @FXML
    private BorderPane root;

    @FXML
    private JFXButton viewBooksBtn;

    @FXML
    private JFXButton viewHistoryBtn;

    @FXML
    private Label lblUserName;

    public void initialize() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/viewBooks.fxml"));
        root.setCenter(view);
        UserBoImpl userBoImpl = new UserBoImpl();
        for (User user : userBoImpl.getAll()) {
            lblUserName.setText(user.getUserName());
        }

    }

    @FXML
    void btnBorrowBooksOnAction(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/borrowBooks.fxml"));
        root.setCenter(view);
    }

    @FXML
    void btnUserDetailsOnAction(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/editUserDetails.fxml"));
        root.setCenter(view);
    }

    @FXML
    void btnViewBooksOnAction(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/viewBooks.fxml"));
        root.setCenter(view);
    }

    @FXML
    void btnViewHistoryOnAction(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/viewHistory.fxml"));
        root.setCenter(view);
    }

    @FXML
    void returnBooksBtnOnAction(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/returnBooks.fxml"));
        root.setCenter(view);
    }
}
