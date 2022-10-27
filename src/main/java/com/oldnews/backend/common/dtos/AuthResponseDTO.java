package com.oldnews.backend.common.dtos;

import com.oldnews.backend.app.dtos.UserDTO;

import java.io.Serializable;

public record AuthResponseDTO(UserDTO user, String token, String refreshToken) implements Serializable {
}
