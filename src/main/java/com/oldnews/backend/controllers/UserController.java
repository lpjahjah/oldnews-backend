package com.oldnews.backend.controllers;

import com.oldnews.backend.common.controllers.BaseController;
import com.oldnews.backend.models.User;
import com.oldnews.backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public class UserController extends BaseController<User, UserRepository> {

    @PostMapping()
    protected ResponseEntity<?> Post() {
        return ResponseEntity.ok().build();
    }

}
