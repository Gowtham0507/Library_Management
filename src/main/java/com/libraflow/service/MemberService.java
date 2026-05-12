package com.libraflow.service;

import com.libraflow.entity.Member;
import com.libraflow.exception.ResourceNotFoundException;
import com.libraflow.exception.ValidationException;
import com.libraflow.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    public Member addMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new ValidationException("Email is already registered.");
        }
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);
        if (!member.getEmail().equals(memberDetails.getEmail()) && memberRepository.existsByEmail(memberDetails.getEmail())) {
            throw new ValidationException("Email is already registered.");
        }
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }
}
