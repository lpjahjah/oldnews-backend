package com.oldnews.backend.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

/**
 * Base interface with basic queries.
 *
 * @typeparam T Object type.
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, UUID> {
    @Override
    Optional<T> findById(UUID id);

    Optional<T> findByIdAndDeletedIsFalse(UUID id);
}
