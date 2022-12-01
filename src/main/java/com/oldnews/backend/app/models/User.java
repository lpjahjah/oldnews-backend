package com.oldnews.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oldnews.backend.app.dtos.UserRegisterDTO;
import com.oldnews.backend.common.models.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements BaseModel {

    /**
     * Construct new User with a null password
     */
    public User(User newUser) {
        this.username = newUser.getUsername();
        this.firstName = newUser.getFirstName();
        this.lastName = newUser.getLastName();
        this.email = newUser.getEmail();
    }

    public User(UserRegisterDTO newUser) {
        this.username = newUser.getUsername();
        this.firstName = newUser.getFirstName();
        this.lastName = newUser.getLastName();
        this.email = newUser.getEmail();
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Override
    public boolean getDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {
    }
}
