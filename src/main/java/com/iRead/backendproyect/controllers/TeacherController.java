package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.services.TeacherService;
import com.iRead.backendproyect.services.TeacherServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher", description = "Teacher management APIs")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/updateTeacher/{teacher_id}")
    public AuthDTO updateTeacher(@PathVariable Long teacher_id,@Valid @RequestBody TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(teacher_id,teacherDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

}

