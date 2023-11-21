package com.iRead.backendproject.controllers;

import com.iRead.backendproject.models.api_story.Student;
import com.iRead.backendproject.models.api_story.StudentActivity;
import com.iRead.backendproject.services.StoryService;
import com.iRead.backendproject.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StoryService storyService;

    @PostMapping("/register")
    public ResponseEntity<Student> enterName(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.enterName(student));
    }

    @PostMapping("/{studentId}/access-story")
    public ResponseEntity<String> accessStory(@PathVariable Long studentId, @RequestParam String accessWord) {
        boolean accessStatus = studentService.accessStory(studentId, accessWord);
        if (accessStatus) {
            return ResponseEntity.ok("Ingreso a la historia exitoso");
        } else {
            return ResponseEntity.ok("No se puede acceder a la historia");
        }
    }

    @PostMapping("/{studentId}/activities")
    public ResponseEntity<StudentActivity> completeActivity(@PathVariable Long studentId, @RequestBody StudentActivity studentActivity, @RequestParam String accessWord) {
        StudentActivity completedActivity = studentService.completeActivity(studentId, studentActivity, accessWord);
        return ResponseEntity.ok(completedActivity);
    }

}
