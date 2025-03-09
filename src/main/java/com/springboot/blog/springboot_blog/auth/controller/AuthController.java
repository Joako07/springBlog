package com.springboot.blog.springboot_blog.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springboot_blog.auth.models.requests.LoginRequest;
import com.springboot.blog.springboot_blog.auth.models.requests.RegisterRequest;
import com.springboot.blog.springboot_blog.auth.models.responses.AuthResponse;
import com.springboot.blog.springboot_blog.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) { 
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    

}
