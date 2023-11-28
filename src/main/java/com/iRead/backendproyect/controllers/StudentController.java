package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.StoryDTORequest;
import com.iRead.backendproyect.dto.StoryResponse;
import com.iRead.backendproyect.models.Student;
import com.iRead.backendproyect.models.StudentActivity;
import com.iRead.backendproyect.services.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Student", description = "Student management APIs")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> enterName(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.enterName(student));
    }

    @PostMapping("/access-story")
    public ResponseEntity<StoryResponse> accessStory(@RequestBody StoryDTORequest storyDTORequest) {
        StoryResponse storyResponse = studentService.accessStory(storyDTORequest);
        return ResponseEntity.ok(storyResponse);
    }

    @PostMapping("/{studentId}/studentActivities/{activityId}")
    public ResponseEntity<StudentActivity> completeActivity(@PathVariable Long studentId, @RequestBody StudentActivity studentActivity, @PathVariable Long activityId) {
        StudentActivity completedActivity = studentService.completeActivity(studentId, studentActivity, activityId);
        return ResponseEntity.ok(completedActivity);
    }

}
