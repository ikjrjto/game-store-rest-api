package com.sumdu.edu.ua.ivan.gamestore.controller;

import com.sumdu.edu.ua.ivan.gamestore.dto.JWTAuthResponse;
import com.sumdu.edu.ua.ivan.gamestore.dto.LoginDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.RegisterDto;
import com.sumdu.edu.ua.ivan.gamestore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = service.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = service.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
