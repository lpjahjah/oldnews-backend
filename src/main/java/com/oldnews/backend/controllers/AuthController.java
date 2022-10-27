package com.oldnews.backend.controllers;

import com.oldnews.backend.common.dtos.AuthRefreshRequestDTO;
import com.oldnews.backend.common.dtos.AuthRefreshResponseDTO;
import com.oldnews.backend.common.dtos.AuthRequestDTO;
import com.oldnews.backend.common.dtos.AuthResponseDTO;
import com.oldnews.backend.models.User;
import com.oldnews.backend.repositories.UserRepository;
import com.oldnews.backend.services.JwtUserDetailsService;
import com.oldnews.backend.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService jwtUserDetailsService,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = jwtUserDetailsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthRequestDTO authenticationRequest
    ) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);

        if (user == null)
            return ResponseEntity.notFound().build();

        final String token = jwtTokenUtil.generateToken(user);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(user, token, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> createRefreshAuthenticationToken(@RequestBody AuthRefreshRequestDTO request) throws Exception {

        String userName = jwtTokenUtil.getUsernameFromToken(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (!jwtTokenUtil.isTokenValid(request.getRefreshToken(), userDetails))
            throw new Exception("Invalid Token");

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthRefreshResponseDTO(token, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}