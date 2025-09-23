package com.sinse.memberservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="member_id")
    private int memberId;
    private String id;
    private String password;
    private String name;
    private String email;
    private String regdate;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name="provider_id")
    private Provider provider;
}
