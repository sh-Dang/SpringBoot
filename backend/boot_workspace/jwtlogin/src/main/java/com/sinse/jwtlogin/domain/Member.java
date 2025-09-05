package com.sinse.jwtlogin.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int memberId;
    private String id;
    private String password;
    private String name;
}
