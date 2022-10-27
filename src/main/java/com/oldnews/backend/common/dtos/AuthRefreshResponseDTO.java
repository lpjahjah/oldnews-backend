package com.oldnews.backend.common.dtos;

import java.io.Serializable;

public record AuthRefreshResponseDTO(String token, String refreshToken) implements Serializable {
}