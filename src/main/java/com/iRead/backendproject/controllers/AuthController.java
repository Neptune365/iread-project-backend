package com.iRead.backendproject.controllers;

import com.iRead.backendproject.dto.AuthDTO;
import com.iRead.backendproject.dto.AuthenticationDTORequest;
import com.iRead.backendproject.dto.TeacherDTO;
import com.iRead.backendproject.dto.TeacherDTORequest;
import com.iRead.backendproject.registration.RegistrationService;
import com.iRead.backendproject.services.TeacherServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
