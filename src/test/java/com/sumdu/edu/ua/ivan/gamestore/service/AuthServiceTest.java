package com.sumdu.edu.ua.ivan.gamestore.service;

import com.sumdu.edu.ua.ivan.gamestore.dto.LoginDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.RegisterDto;
import com.sumdu.edu.ua.ivan.gamestore.entity.Role;
import com.sumdu.edu.ua.ivan.gamestore.entity.User;
import com.sumdu.edu.ua.ivan.gamestore.repository.RoleRepository;
import com.sumdu.edu.ua.ivan.gamestore.repository.UserRepository;
import com.sumdu.edu.ua.ivan.gamestore.security.JwtTokenProvider;
import com.sumdu.edu.ua.ivan.gamestore.service.impl.AuthServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    public static final String MOCKED_TOKEN = "mockedToken";
    public static final String USERNAME = "john.doe";
    public static final String EMAIL = "john.doe@example.com";
    public static final Boolean IS_NOT_EXIST = false;
    public static final String ROLE_USER = "ROLE_USER";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "encodedPassword";
    public static final String SUCCESSFULLY = "User registered successfully!";
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private AuthenticationManager manager;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private AuthServiceImpl service;

    private LoginDto loginDto;
    private RegisterDto registerDto;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("testUser");
        loginDto.setPassword("testUser");

        registerDto = new RegisterDto();
        registerDto.setName("John Doe");
        registerDto.setUsername(USERNAME);
        registerDto.setEmail(EMAIL);
        registerDto.setPassword(PASSWORD);
    }

    @Test
    void AuthService_Login_ReturnToken() {
        Authentication authentication = mock(Authentication.class);
        when(manager.authenticate(any())).thenReturn(authentication);

        when(tokenProvider.generateToken(authentication))
                .thenReturn(MOCKED_TOKEN);

        String result = service.login(loginDto);

        verify(manager, times(1)).authenticate(any());
        verify(tokenProvider, times(1)).generateToken(authentication);

        Assertions.assertThat(result).isEqualTo(MOCKED_TOKEN);
    }

    @Test
    void AuthService_Register_ReturnSuccessfullyMessage() {
        when(userRepository.existsByUsername(USERNAME)).thenReturn(IS_NOT_EXIST);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(IS_NOT_EXIST);

        Role userRole = new Role();
        userRole.setName(ROLE_USER);

        when(roleRepository.findByName(ROLE_USER)).thenReturn(Optional.of(userRole));
        when(encoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        String result = service.register(registerDto);

        verify(userRepository, times(1)).existsByUsername(USERNAME);
        verify(userRepository, times(1)).existsByEmail(EMAIL);
        verify(userRepository, times(1)).save(any(User.class));

        // Assertions
        Assertions.assertThat(SUCCESSFULLY).isEqualTo(result);
    }
}