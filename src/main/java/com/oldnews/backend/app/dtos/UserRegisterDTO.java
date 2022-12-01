package com.oldnews.backend.app.dtos;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;
}
