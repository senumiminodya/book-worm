package org.example.entity;

import jakarta.persistence.*;

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

    @OneToOne(mappedBy = "branch")
    private Book book;

    public Branch() {
    }

    public Branch(int id, String branchName, String address, String contactNo) {
        this.setId(id);
        this.setBranchName(branchName);
        this.setAddress(address);
        this.setContactNo(contactNo);
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
                "id=" + getId() +
                ", branchName='" + getBranchName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contactNo='" + getContactNo() + '\'' +
                '}';
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
