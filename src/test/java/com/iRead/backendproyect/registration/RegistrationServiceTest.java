package com.iRead.backendproyect.registration;

import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.dto.TeacherDTORequest;
import com.iRead.backendproyect.email.EmailSender;
import com.iRead.backendproyect.mapper.TeacherMapper;
import com.iRead.backendproyect.models.Role;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.registration.token.ConfirmationToken;
import com.iRead.backendproyect.registration.token.ConfirmationTokenService;
import com.iRead.backendproyect.services.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherRegistrationServiceTest {

    @Mock
    private TeacherServiceImpl teacherServiceImpl;

    @Mock
    private EmailSender emailSender;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @InjectMocks
    private RegistrationService teacherRegistrationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void register_ShouldReturnTeacherDTO() {
        // Arrange
        TeacherDTORequest request = new TeacherDTORequest("John", "Doe", "john@example.com", "password", Role.USER);
        Teacher teacher = new Teacher(request.getName(), request.getSurname(), request.getEmail(), request.getPassword());
        String token = "fakeToken";
        String confirmationLink = "http://localhost:8081/api/auth/confirm?token=" + token;

        when(teacherServiceImpl.singUpUser(any(Teacher.class))).thenReturn(token);
        doNothing().when(emailSender).send(anyString(), anyString());
        when(teacherMapper.mapToDTO(teacher)).thenReturn(new TeacherDTO(teacher.getName(), teacher.getSurname(), teacher.getEmail()));

        // Act
        TeacherDTO result = teacherRegistrationService.register(request);

        // Assert
        assertNotNull(result);
        assertEquals(teacher.getName(), result.getName());
        assertEquals(teacher.getSurname(), result.getSurname());
        assertEquals(teacher.getEmail(), result.getEmail());

        verify(teacherServiceImpl, times(1)).singUpUser(any(Teacher.class));
        verify(emailSender, times(1)).send(anyString(), anyString());
        verify(teacherMapper, times(1)).mapToDTO(teacher);
    }


    @Test
    void confirmToken_WhenTokenIsFoundAndNotConfirmedAndNotExpired_ShouldReturnConfirmed() {
        // Arrange
        String token = "validToken";
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                new Teacher("John", "Doe", "john@example.com", "password")
        );

        when(confirmationTokenService.getToken(eq(token))).thenReturn(Optional.of(confirmationToken));
        when(confirmationTokenService.setConfirmedAt(eq(token))).thenReturn(1);

        // Act
        String result = teacherRegistrationService.confirmToken(token);

        // Assert
        assertEquals("confirmed", result);

        verify(confirmationTokenService, times(1)).getToken(token);
        verify(confirmationTokenService, times(1)).setConfirmedAt(token);
        verify(teacherServiceImpl, times(1)).enableUser(confirmationToken.getTeacher().getEmail());

    }

    @Test
    void confirmToken_WhenTokenNotFound_ShouldThrowException() {
        // Arrange
        String nonExistingToken = "nonExistingToken";
        when(confirmationTokenService.getToken(eq(nonExistingToken))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> teacherRegistrationService.confirmToken(nonExistingToken));
    }


    @Test
    void confirmToken_WhenTokenAlreadyConfirmed_ShouldThrowException() {
        // Arrange
        String confirmedToken = "confirmedToken";
        ConfirmationToken confirmedConfirmationToken = new ConfirmationToken(
                confirmedToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                new Teacher("John", "Doe", "john@example.com", "password")
        );
        confirmedConfirmationToken.setConfirmedAt(LocalDateTime.now());
        when(confirmationTokenService.getToken(eq(confirmedToken))).thenReturn(Optional.of(confirmedConfirmationToken));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> teacherRegistrationService.confirmToken(confirmedToken));
    }

    @Test
    void confirmToken_WhenTokenExpired_ShouldThrowException() {
        // Arrange
        String expiredToken = "expiredToken";
        ConfirmationToken expiredConfirmationToken = new ConfirmationToken(
                expiredToken,
                LocalDateTime.now().minusDays(1),  // Token expirado
                LocalDateTime.now().minusDays(1).plusMinutes(15),
                new Teacher("John", "Doe", "john@example.com", "password")
        );
        when(confirmationTokenService.getToken(eq(expiredToken))).thenReturn(Optional.of(expiredConfirmationToken));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> teacherRegistrationService.confirmToken(expiredToken));
    }

}