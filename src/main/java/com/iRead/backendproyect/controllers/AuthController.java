package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTO;
import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.services.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
@RequiredArgsConstructor
public class AuthController {

    private final TeacherService teacherService;

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@Valid @RequestBody TeacherDTO request){
        return ResponseEntity.ok(teacherService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthDTO> authenticate(@Valid @RequestBody AuthenticationDTO request){
        return ResponseEntity.ok(teacherService.authenticate(request));
    }

}
