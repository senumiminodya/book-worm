package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    private int id;

    @Column(name = "branchName")
    private String branchName;

    @Column(name = "address")
    private String address;

    @Column(name = "contactNo")
    private String contactNo;

    public Branch() {
    }

    public Branch(int id, String branchName, String address, String contactNo) {
        this.id = id;
        this.branchName = branchName;
        this.address = address;
        this.contactNo = contactNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
