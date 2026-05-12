package com.libraflow.controller;

import com.libraflow.entity.Member;
import com.libraflow.entity.IssueRecord;
import com.libraflow.service.MemberService;
import com.libraflow.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final IssueService issueService;

    public MemberController(MemberService memberService, IssueService issueService) {
        this.memberService = memberService;
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return new ResponseEntity<>(memberService.addMember(member), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<IssueRecord>> getMemberIssues(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getMemberIssues(id));
    }
}
