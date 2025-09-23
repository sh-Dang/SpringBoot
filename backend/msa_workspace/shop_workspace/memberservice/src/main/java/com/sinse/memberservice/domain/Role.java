package com.sinse.memberservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int roleId;
    @Column(name="role_name")
    private String roleName;
}
