package com.sinse.jwtredis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="role")
@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    private String roleName;
}
