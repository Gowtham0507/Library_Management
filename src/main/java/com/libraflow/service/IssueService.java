package com.libraflow.service;

import com.libraflow.dto.IssueRequestDto;
import com.libraflow.entity.Book;
import com.libraflow.entity.IssueRecord;
import com.libraflow.entity.Member;
import com.libraflow.exception.ResourceNotFoundException;
import com.libraflow.exception.ValidationException;
import com.libraflow.repository.BookRepository;
import com.libraflow.repository.IssueRecordRepository;
import com.libraflow.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssueService {

    private final IssueRecordRepository issueRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public IssueService(IssueRecordRepository issueRepository,
                        BookRepository bookRepository,
                        MemberRepository memberRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public List<IssueRecord> getAllIssues() {
        return issueRepository.findAll();
    }

    public List<IssueRecord> getActiveIssues() {
        return issueRepository.findByReturnDateIsNull();
    }

    public List<IssueRecord> getMemberIssues(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
        return issueRepository.findByMember(member);
    }

    @Transactional
    public IssueRecord issueBook(IssueRequestDto request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + request.getMemberId()));

        if (!book.getAvailability()) {
            throw new ValidationException("Book is currently not available.");
        }

        long activeIssues = issueRepository.countByMemberAndReturnDateIsNull(member);
        if (activeIssues >= 3) {
            throw new ValidationException("Member has already reached the maximum limit of 3 issued books.");
        }

        boolean alreadyIssued = issueRepository.existsByMemberAndBookAndReturnDateIsNull(member, book);
        if (alreadyIssued) {
            throw new ValidationException("Member has already issued this book and hasn't returned it yet.");
        }

        book.setAvailability(false);
        bookRepository.save(book);

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setMember(member);
        issueRecord.setIssueDate(LocalDate.now());

        return issueRepository.save(issueRecord);
    }

    @Transactional
    public IssueRecord returnBook(Long issueId) {
        IssueRecord issueRecord = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found with id: " + issueId));

        if (issueRecord.getReturnDate() != null) {
            throw new ValidationException("Book is already returned.");
        }

        issueRecord.setReturnDate(LocalDate.now());

        Book book = issueRecord.getBook();
        book.setAvailability(true);
        bookRepository.save(book);

        return issueRepository.save(issueRecord);
    }
}
