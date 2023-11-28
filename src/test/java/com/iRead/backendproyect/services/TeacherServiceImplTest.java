package com.iRead.backendproyect.services;

import com.iRead.backendproyect.config.Jwt.JwtService;
import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTORequest;
import com.iRead.backendproyect.exception.EmailExistsException;
import com.iRead.backendproyect.exception.NoSuchElementException;
import com.iRead.backendproyect.token.Token;
import com.iRead.backendproyect.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.iRead.backendproyect.repositories.TeacherRepository;
import com.iRead.backendproyect.registration.token.ConfirmationTokenService;
import com.iRead.backendproyect.registration.token.ConfirmationToken;
import com.iRead.backendproyect.models.Teacher;

class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TeacherServiceImpl teacherServiceImpl;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void signUpUser_WhenEmailDoesNotExist_ShouldReturnToken() {
        // Arrange
        Teacher user = new Teacher("Luis", "Sánchez", "lsanchezp1@gmail.com", "123luissanchez");
        when(teacherRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // Act
        String token = teacherServiceImpl.singUpUser(user);

        // Assert
        assertNotNull(token);
        verify(teacherRepository, times(1)).save(user);
        verify(confirmationTokenService, times(1)).saveConfirmationToken(any(ConfirmationToken.class));
    }

    @Test
    void signUpUser_WhenEmailExists_ShouldThrowException() {
        // Arrange
        Teacher existingUser = new Teacher("Luis", "Sánchez", "lsanchezp1@gmail.com", "123luissanchez");
        when(teacherRepository.findUserByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(EmailExistsException.class, () -> teacherServiceImpl.singUpUser(existingUser));
        verify(teacherRepository, never()).save(existingUser);
        verify(confirmationTokenService, never()).saveConfirmationToken(any(ConfirmationToken.class));
    }


    @Test
    void authenticate_Successful() {
        // Arrange
        AuthenticationDTORequest request = new AuthenticationDTORequest();
        request.setEmail("correo@ejemplo.com");
        request.setPassword("password");

        Teacher user = new Teacher();
        user.setId(1L);

        when(teacherRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyString(), anyMap(), any(Teacher.class))).thenReturn("fakeToken");
        when(tokenRepository.findAllValidTokensBy(anyLong())).thenReturn(List.of(new Token()));

        // Act
        AuthDTO result = teacherServiceImpl.authenticate(request);

        // Assert
        assertNotNull(result);
        assertEquals("fakeToken", result.getToken());

    }
    @Test
    void authenticate_UserNotFound() {
        // Arrange
        AuthenticationDTORequest request = new AuthenticationDTORequest();
        request.setEmail("correo_no_existente@ejemplo.com");
        request.setPassword("password");

        when(teacherRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> {
            teacherServiceImpl.authenticate(request);
        });
    }

}