package com.sumdu.edu.ua.ivan.gamestore.service.impl;

import com.sumdu.edu.ua.ivan.gamestore.dto.LoginDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.RegisterDto;
import com.sumdu.edu.ua.ivan.gamestore.entity.Role;
import com.sumdu.edu.ua.ivan.gamestore.entity.User;
import com.sumdu.edu.ua.ivan.gamestore.exception.StoreAPIException;
import com.sumdu.edu.ua.ivan.gamestore.repository.RoleRepository;
import com.sumdu.edu.ua.ivan.gamestore.repository.UserRepository;
import com.sumdu.edu.ua.ivan.gamestore.security.JwtTokenProvider;
import com.sumdu.edu.ua.ivan.gamestore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Username is already exist!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Email is already exist!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(encoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!";
    }
}
