package com.libraflow.dto;

import jakarta.validation.constraints.NotNull;

public class IssueRequestDto {

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    public IssueRequestDto() {}

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
}
