package com.libraflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // --- Constructors ---
    public Member() {}

    public Member(Long memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    // --- Getters ---
    public Long getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    // --- Setters ---
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
