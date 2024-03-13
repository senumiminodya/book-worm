package org.example.tm;

import org.example.cm.BranchCm;

public class BookTm {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String availability;
    private String branchName;

    public BookTm() {
    }

    public BookTm(int id, String title, String author, String genre, String availability, String branchName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
        this.branchName = branchName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(BranchCm branchName) {
        this.branchName = String.valueOf(branchName);
    }

    @Override
    public String toString() {
        return "BookTm{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", availability='" + availability + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
