package com.libraflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Boolean availability = true;

    // --- Constructors ---
    public Book() {}

    public Book(Long bookId, String title, String author, Boolean availability) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.availability = availability;
    }

    // --- Getters ---
    public Long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Boolean getAvailability() { return availability; }

    // --- Setters ---
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvailability(Boolean availability) { this.availability = availability; }
}
