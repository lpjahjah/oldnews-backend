package com.oldnews.backend.common.repositories;

import com.oldnews.backend.common.models.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Base interface with basic queries.
 *
 * @typeparam T Object type.
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseModel> extends JpaRepository<T, UUID> {
    @Override
    Optional<T> findById(UUID id);

    List<T> findAllByDeletedIsFalse();

    Optional<T> findByIdAndDeletedIsFalse(UUID id);
}
