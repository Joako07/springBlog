package com.springboot.blog.springboot_blog.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.springboot_blog.auth.jwt.JwtService;
import com.springboot.blog.springboot_blog.auth.models.requests.LoginRequest;
import com.springboot.blog.springboot_blog.auth.models.requests.RegisterRequest;
import com.springboot.blog.springboot_blog.auth.models.responses.AuthResponse;
import com.springboot.blog.springboot_blog.auth.repository.UserRepository;
import com.springboot.blog.springboot_blog.auth.user.Role;
import com.springboot.blog.springboot_blog.auth.user.UserEntity;
import com.springboot.blog.springboot_blog.exceptions.BadRequestApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    //loguea un usuario
    public AuthResponse login (LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw BadRequestApiException.builder()
                    .error(e.getMessage())
                    .message("El usuario o contrasena son incorrectos.")
                    .build();
        }

        UserDetails user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
        .token(token)
        .build();
    }

    //Registra un nuevo usuario
    public AuthResponse register (RegisterRequest request){
        UserEntity user = UserEntity.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ROLE_USER)
        .build();

        userRepository.save(user);

        return AuthResponse.builder()
        .token(jwtService.getToken(user))
        .build();
    }

}
