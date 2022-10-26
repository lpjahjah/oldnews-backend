package com.oldnews.backend.common.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequestDTO implements Serializable {

    private String username;

    private String password;

}
