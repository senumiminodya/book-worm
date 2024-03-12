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
import org.example.entity.Branch;

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
        String branchName = txtName.getText();
        if (!branchName.isEmpty()) {
            if (branchBo.deleteBranch(branchName)) {
                new Alert(Alert.AlertType.INFORMATION, "Branch deleted successfully!").show();
                txtBranchId.clear();
                txtName.clear();
                txtAddress.clear();
                txtContactNo.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error deleting branch from the database").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter the branch name to delete").show();
        }
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
            if (branchBo.update(branchDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Branch updated successfully!").show();
                txtBranchId.clear();
                txtName.clear();
                txtAddress.clear();
                txtContactNo.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error updating branch in the database").show();
            }
        }
    }

    @FXML
    void txtNameSearchOnAction(ActionEvent event) {
        fetchAndDisplayBranchDetails();

    }

    private void fetchAndDisplayBranchDetails() {
        String name = txtName.getText().trim(); // Trim the input to remove leading/trailing spaces
        if (!name.isEmpty()) { // Check if the input is not empty
            Branch branch = branchBo.getBranches(name);
            if (branch != null) {
                txtBranchId.setText(String.valueOf(branch.getId()));
                txtAddress.setText(branch.getAddress());
                txtContactNo.setText(branch.getContactNo());
            } else {
                // Branch not found
                new Alert(Alert.AlertType.ERROR, "Branch not found").show();
            }
        } else {
            // Show error if no branch name is entered
            new Alert(Alert.AlertType.ERROR, "Please enter the branch name to search").show();
        }
    }

}
