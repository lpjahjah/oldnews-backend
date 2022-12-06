package com.oldnews.backend.common.repositories;

import com.oldnews.backend.common.models.Migration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MigrationRepository extends JpaRepository<Migration, Integer> {
}
