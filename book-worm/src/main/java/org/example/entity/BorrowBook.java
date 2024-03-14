package org.example.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name = "borrowedBooks")
public class BorrowBook {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "author")
    private String author;
    @Column(name = "genre")
    private String genre;
    @Column(name = "borrowDate")
    private Date borrowDate;
    @Column(name = "dueDate")
    private Date dueDate;
    private boolean isReturned;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId")
    private User user;

    public BorrowBook() {
    }

    public BorrowBook(int id, String author, String genre, Date borrowDate, Date dueDate, Book book, User user) {
        this.id = id;
        this.author = author;
        this.genre = genre;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.book = book;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
