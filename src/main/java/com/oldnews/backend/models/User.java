package com.oldnews.backend.models;

import com.oldnews.backend.common.dtos.AuthRequestDTO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Data
@Table(name = "user")
public class User {

    public User(AuthRequestDTO authRequestDTO) {
        this.username = authRequestDTO.getUsername();
        this.password = authRequestDTO.getPassword();
    }

    public User() {
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
