package com.oldnews.backend.app.repositories;


import com.oldnews.backend.app.models.User;
import com.oldnews.backend.common.repositories.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);

}
