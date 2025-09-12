package com.sinse.jwtredis.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Table(name="member")
@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    private String id;
    private String password;
    private String name;
    private String email;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
}
