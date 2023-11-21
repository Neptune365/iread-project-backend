package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.*;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.registration.RegistrationService;
import com.iRead.backendproyect.services.TeacherServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final TeacherServiceImpl teacherService;

    @PostMapping("/register")
    public ResponseEntity<TeacherDTO> register(@Valid @RequestBody TeacherDTORequest request){
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthDTO> authenticate(@Valid @RequestBody AuthenticationDTORequest request){
        return ResponseEntity.ok(teacherService.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Optional<Teacher> optionalTeacher = teacherService.findTeacherByEmail(request.getEmail());

        if (optionalTeacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Teacher teacher = optionalTeacher.get();

        String recoveryToken = teacherService.generateRecoveryToken(teacher);
        teacherService.sendRecoveryTokenByEmail(teacher.getEmail(), recoveryToken);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        teacherService.resetPassword(request);
        return ResponseEntity.ok().build();
    }

}
