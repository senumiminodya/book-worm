package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.*;
import org.example.dto.AdminDto;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.tm.BranchTm;

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

    @FXML
    private TableView<BranchTm> tblBranch;
    @FXML
    private TableColumn<?, ?> colBranchAddress;

    @FXML
    private TableColumn<?, ?> colBranchId;

    @FXML
    private TableColumn<?, ?> colBranchName;

    @FXML
    private TableColumn<?, ?> colContactNo;

    BranchBo branchBo = new BranchBoImpl();

    public void initialize() throws SQLException {
        int nextBranchId;
        if (branchBo.isBranchTableEmpty()) {
            nextBranchId = 1; // Set ID to 1 if no user is saved in the database
        } else {
            nextBranchId = branchBo.getNextUserId(); // Get the next ID from the database
        }
        txtBranchId.setText(String.valueOf(nextBranchId));
        setCellvalueFactory();
        tableListener();
        loadAllBranches();
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
    private void loadAllBranches() {
        ObservableList<BranchTm> observableList = FXCollections.observableArrayList();
        List<Branch> branchesList = branchBo.getAll(); // Assuming you have a branchesBO object
        for (Branch branch : branchesList) {
            // Create BranchTm object using branch details and add it to the observable list
            observableList.add(new BranchTm(branch.getId(), branch.getBranchName(), branch.getAddress(), branch.getContactNo()));
        }
        // Clear existing items and set the new observable list
        tblBranch.getItems().clear();
        tblBranch.setItems(observableList);
    }

    private void setCellvalueFactory() {
        colBranchId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBranchName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBranchAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
    }
    private void tableListener() {
        tblBranch.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BranchTm selectedBranch = (BranchTm) newValue;
                txtBranchId.setText(String.valueOf(selectedBranch.getId()));
                txtName.setText(selectedBranch.getName());
                txtAddress.setText(selectedBranch.getAddress());
                txtContactNo.setText(selectedBranch.getContactNo());
            }
        });
    }

}
