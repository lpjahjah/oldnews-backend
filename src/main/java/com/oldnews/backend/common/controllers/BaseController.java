package com.oldnews.backend.common.controllers;

import com.oldnews.backend.common.models.BaseModel;
import com.oldnews.backend.common.repositories.BaseRepository;
import com.oldnews.backend.utils.ObjectMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Base controller with generic CRUD.
 *
 * @typeparam T Object type.
 * @typeparam Repo Object's repository
 */
public abstract class BaseController<T extends BaseModel, Repo extends BaseRepository<T>> {
    protected final Repo repository;

    protected final ObjectMapperUtil mapper;

    public BaseController(Repo repository, ObjectMapperUtil mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping()
    public ResponseEntity<List<T>> GetAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> Post(
            @RequestBody T body
    ) {
        T object = repository.save(body);
        return ResponseEntity.ok(object);
    }

//    @PostMapping("{id}")
//    public ResponseEntity<?> Put(
//            @PathVariable String id,
//            @RequestBody T body
//    ) {
//        UUID uuid = UUID.fromString("id");
//        T persisted = repository.findById(uuid).orElse(null);
//
//        const persisted mapper.map(body);
//
//        repository.save(body);
//        return ResponseEntity.ok().build();
//    }
}
