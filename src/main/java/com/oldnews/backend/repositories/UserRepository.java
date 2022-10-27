package com.oldnews.backend.repositories;


import com.oldnews.backend.common.repositories.BaseRepository;
import com.oldnews.backend.models.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);

}
