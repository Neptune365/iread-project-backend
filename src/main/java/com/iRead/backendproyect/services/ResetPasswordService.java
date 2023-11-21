package com.iRead.backendproyect.services;

import com.iRead.backendproyect.config.Jwt.JwtService;
import com.iRead.backendproyect.dto.ResetPasswordRequest;
import com.iRead.backendproyect.email.EmailSender;
import com.iRead.backendproyect.exception.NoSuchElementException;
import com.iRead.backendproyect.mapper.TeacherMapper;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.registration.token.ConfirmationTokenService;
import com.iRead.backendproyect.repositories.TeacherRepository;
import com.iRead.backendproyect.token.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResetPasswordService {


    public final TeacherRepository teacherRepository;
    public final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    public String generateRecoveryToken(Teacher teacher) {
        String token = generateRandomToken();
        teacher.setRecoveryToken(token);
        teacher.setRecoveryTokenExpiry(LocalDateTime.now().plusHours(1));

        teacherRepository.save(teacher);

        return token;
    }

    private static String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[4];
        secureRandom.nextBytes(tokenBytes);

        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

        return token.substring(0, 6);
    }

    public void sendRecoveryTokenByEmail(String email, String recoveryToken) {
        String subject = "Recuperación de Contraseña";
        String body = buildEmail(subject, recoveryToken);

        emailSender.send(email, body);
    }

    private String buildEmail(String subject, String recoveryToken) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "            <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "              <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" + subject + "</span>\n" +
                "            </td>\n" +
                "</div>" +
                "Hola,\n\n" +
                "Has solicitado recuperar tu contraseña. Utiliza el siguiente token:\n\n" +
                recoveryToken +
                "\n\nEste token es válido por un tiempo limitado. Si no has solicitado esta recuperación, ignora este correo.\n\n" +
                "Saludos,\nTu Aplicación";
    }

    public void resetPassword(ResetPasswordRequest request)  {
        Optional<Teacher> optionalTeacher = teacherRepository.findUserByEmail(request.getEmail());

        if (optionalTeacher.isEmpty()) {
            throw new NoSuchElementException("Email no registrado");
        }

        Teacher teacher = optionalTeacher.get();

        if (isValidRecoveryToken(teacher, request.getRecoveryToken()) &&
                isPasswordConfirmationValid(request.getNewPassword(), request.getConfirmationPassword())) {
            teacher.setPassword(passwordEncoder.encode(request.getNewPassword()));
            teacherRepository.save(teacher);
        } else {
            throw new IllegalStateException("Token no válido");
        }
    }

    private boolean isValidRecoveryToken(Teacher teacher, String recoveryToken) {
        return teacher.getRecoveryToken().equals(recoveryToken) &&
                teacher.getRecoveryTokenExpiry().isAfter(LocalDateTime.now());
    }

    private boolean isPasswordConfirmationValid(String newPassword, String confirmationPassword) {
        return newPassword != null && newPassword.equals(confirmationPassword);
    }

}
