package com.oldnews.backend.common.dtos;

import com.oldnews.backend.models.User;

import java.io.Serializable;

public record AuthResponseDTO(User user, String token, String refreshToken) implements Serializable {
}
