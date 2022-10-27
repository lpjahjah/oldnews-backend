package com.oldnews.backend.common.dtos;

import lombok.Data;

@Data
public class AuthRefreshRequestDTO {
    private String refreshToken;
}
