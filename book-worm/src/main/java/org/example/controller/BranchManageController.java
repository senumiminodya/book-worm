package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.*;
import org.example.dto.AdminDto;
import org.example.dto.BranchDto;

import java.sql.SQLException;
import java.util.List;

public class BranchManageController {
    @FXML
    private AnchorPane branchAnchorpane;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBranchId;

    BranchBo branchBo = new BranchBoImpl();

    public void initialize() throws SQLException {
        int nextBranchId;
        if (branchBo.isBranchTableEmpty()) {
            nextBranchId = 1; // Set ID to 1 if no user is saved in the database
        } else {
            nextBranchId = branchBo.getNextUserId(); // Get the next ID from the database
        }
        txtBranchId.setText(String.valueOf(nextBranchId));
    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
        txtBranchId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContactNo.clear();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtBranchId.getText());
        branchBo.deleteBranch(id);
        new Alert(Alert.AlertType.INFORMATION, "Branch deleted successfully!").show();
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtBranchId.getText());
        String branchName = txtName.getText();
        String address = txtAddress.getText();
        String contactNo = txtContactNo.getText();

        if (branchName.isEmpty() || address.isEmpty() || contactNo.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fill All Fields").show();
            txtName.requestFocus();
            return;
        } else {
            BranchDto branchDto = new BranchDto(id, branchName, address, contactNo);
            if (branchBo.saveBranch(branchDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Branch saved successfully!").show();
                txtBranchId.clear();
                txtName.clear();
                txtAddress.clear();
                txtContactNo.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error saving branch to the database").show();
            }
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

    }
}
