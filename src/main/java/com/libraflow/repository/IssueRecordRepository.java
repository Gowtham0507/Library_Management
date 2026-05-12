package com.libraflow.repository;

import com.libraflow.entity.Book;
import com.libraflow.entity.IssueRecord;
import com.libraflow.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
    List<IssueRecord> findByMember(Member member);
    List<IssueRecord> findByMemberAndReturnDateIsNull(Member member);
    long countByMemberAndReturnDateIsNull(Member member);
    boolean existsByMemberAndBookAndReturnDateIsNull(Member member, Book book);
    List<IssueRecord> findByReturnDateIsNull();
}
