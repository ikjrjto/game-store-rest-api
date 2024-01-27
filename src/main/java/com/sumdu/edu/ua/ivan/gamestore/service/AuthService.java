package com.sumdu.edu.ua.ivan.gamestore.service;

import com.sumdu.edu.ua.ivan.gamestore.dto.LoginDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
