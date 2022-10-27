package com.oldnews.backend.common.controllers;

import com.oldnews.backend.common.repositories.BaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Base controller with generic CRUD.
 *
 * @typeparam T Object type.
 * @typeparam Repo Object's repository
 */
@RestController
public abstract class BaseController<T, Repo extends BaseRepository<T>> {
    protected Repo repository;

    public BaseController(Repo repository) {
        this.repository = repository;
    }

    public BaseController() {
    }

    @GetMapping()
    public ResponseEntity<List<T>> GetAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> Post(
            @RequestBody T body
    ) {
        return ResponseEntity.ok().build();
    }
}
