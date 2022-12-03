package com.oldnews.backend.common.controllers;

import com.oldnews.backend.common.models.BaseModel;
import com.oldnews.backend.common.repositories.BaseRepository;
import com.oldnews.backend.utils.ObjectMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<T> GetById(
            @PathVariable String id
    ) {
        UUID uuid = UUID.fromString(id);

        T entity = repository.findByIdAndDeletedIsFalse(uuid).orElse(null);

        return ResponseEntity.ok(entity);
    }


    @PostMapping()
    public ResponseEntity<?> Post(
            @RequestBody T body
    ) {
        T object = repository.save(body);
        return ResponseEntity.ok(object);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> Put(
            @PathVariable String id,
            @RequestBody T body
    ) {
        try {
            UUID uuid = UUID.fromString(id);

            T object = repository.findByIdAndDeletedIsFalse(uuid).orElse(null);

            if (object == null)
                return ResponseEntity.notFound().build();

            body.setId(object.getId());

            T updatedObject = repository.save(body);
            return ResponseEntity.ok(updatedObject);
        } catch (Exception ex) {
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Delete(
            @PathVariable String id
    ) {
        try {
            UUID uuid = UUID.fromString(id);

            T object = repository.findByIdAndDeletedIsFalse(uuid).orElse(null);

            if (object == null)
                return ResponseEntity.notFound().build();

            T deletedObject = repository.save(object);
            return ResponseEntity.ok(deletedObject);
        } catch (Exception ex) {
            return ResponseEntity.status(400).build();
        }
    }
}
