package com.oldnews.backend.app.dtos;

import com.oldnews.backend.app.models.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;
}
