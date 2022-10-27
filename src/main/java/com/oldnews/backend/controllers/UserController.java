package com.oldnews.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping()
    public ResponseEntity<?> Post() {
        return ResponseEntity.ok().build();
    }

}
