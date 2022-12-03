package com.oldnews.backend.services;

import com.oldnews.backend.app.dtos.UserRegisterDTO;
import com.oldnews.backend.app.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder bcryptEncoder;

    public JwtUserDetailsService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.bcryptEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.oldnews.backend.app.models.User user = userRepository.findByUsernameAndDeletedIsFalse(username).orElse(null);

        if (user != null)
            return new User(user.getUsername(), user.getPassword(), Collections.emptyList());

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public com.oldnews.backend.app.models.User save(UserRegisterDTO user) {
        com.oldnews.backend.app.models.User newUser = new com.oldnews.backend.app.models.User(user);
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

}