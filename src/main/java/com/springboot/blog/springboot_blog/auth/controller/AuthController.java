package com.springboot.blog.springboot_blog.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springboot_blog.auth.models.requests.LoginRequest;
import com.springboot.blog.springboot_blog.auth.models.requests.RegisterRequest;
import com.springboot.blog.springboot_blog.auth.models.responses.AuthResponse;
import com.springboot.blog.springboot_blog.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Tag(name = "Autenticacion", description = "Endpoints de autenticacion")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login User", description = "Autentica un usuario y le devuelve un Token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Autentica a un usuario con el username y la password", 
            required = true, 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class))),
            responses = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado con éxito") })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(summary = "Register User", description = "Crea un usuario nuevo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Crea un nuevo usuario con el username, password y role", 
            required = true, 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterRequest.class))), 
            responses = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito") })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
