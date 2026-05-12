package com.libraflow.controller;

import com.libraflow.dto.IssueRequestDto;
import com.libraflow.entity.IssueRecord;
import com.libraflow.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/issue")
    public ResponseEntity<IssueRecord> issueBook(@Valid @RequestBody IssueRequestDto request) {
        return new ResponseEntity<>(issueService.issueBook(request), HttpStatus.CREATED);
    }

    @PutMapping("/return/{issueId}")
    public ResponseEntity<IssueRecord> returnBook(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.returnBook(issueId));
    }

    @GetMapping
    public ResponseEntity<List<IssueRecord>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/active")
    public ResponseEntity<List<IssueRecord>> getActiveIssues() {
        return ResponseEntity.ok(issueService.getActiveIssues());
    }
}
