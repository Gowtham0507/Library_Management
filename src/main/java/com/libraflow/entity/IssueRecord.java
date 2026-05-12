package com.libraflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "issue_records")
public class IssueRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate issueDate;

    private LocalDate returnDate;

    // --- Constructors ---
    public IssueRecord() {}

    public IssueRecord(Long issueId, Book book, Member member, LocalDate issueDate, LocalDate returnDate) {
        this.issueId = issueId;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // --- Getters ---
    public Long getIssueId() { return issueId; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    // --- Setters ---
    public void setIssueId(Long issueId) { this.issueId = issueId; }
    public void setBook(Book book) { this.book = book; }
    public void setMember(Member member) { this.member = member; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
