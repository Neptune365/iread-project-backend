package com.iRead.backendproject.controllers;

import com.iRead.backendproject.dto.AuthDTO;
import com.iRead.backendproject.dto.TeacherDTORequest;
import com.iRead.backendproject.models.Teacher;
import com.iRead.backendproject.services.TeacherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher", description = "Teacher management APIs")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/updateTeacher/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public AuthDTO updateTeacher(@PathVariable Long teacherId,@Valid @RequestBody TeacherDTORequest teacherDTORequest) {
        return teacherService.updateTeacher(teacherId, teacherDTORequest);
    }

    @GetMapping("/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long teacherId) {
        Teacher teacher = teacherService.findTeacherById(teacherId);
        return ResponseEntity.ok(teacher);
    }

}

