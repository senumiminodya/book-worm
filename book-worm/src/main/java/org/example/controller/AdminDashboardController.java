package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminDashboardController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton booksBtn;

    @FXML
    private JFXButton branchBtn;

    @FXML
    private JFXButton userBtn;

    public void initialize() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/booksManage.fxml"));
        borderPane.setCenter(view);
    }

    @FXML
    void booksOnMouseClicked(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/booksManage.fxml"));
        borderPane.setCenter(view);
    }

    @FXML
    void userOnMouseClicked(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/userManage.fxml"));
        borderPane.setCenter(view);
    }

    @FXML
    void branchOnMouseClicked(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/branchManage.fxml"));
        borderPane.setCenter(view);
    }


}
