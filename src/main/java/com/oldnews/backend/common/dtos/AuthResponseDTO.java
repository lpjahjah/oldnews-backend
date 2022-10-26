package com.oldnews.backend.common.dtos;

import java.io.Serializable;

public record AuthResponseDTO(String jwtToken) implements Serializable {

}
