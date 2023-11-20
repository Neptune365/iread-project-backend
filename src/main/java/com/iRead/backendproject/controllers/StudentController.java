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

    @PostMapping("/")
    public ResponseEntity<Student> enterName(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.enterName(student));
    }

    @GetMapping("/accessStory/{accessWord}")
    public boolean accessToStoryByAccessWord(@PathVariable String accessWord) {
        return storyService.findStoryByAccessWord(accessWord);
    }

    @PostMapping("/{studentId}/activities")
    public ResponseEntity<StudentActivity> completeActivity(@PathVariable long studentId, @RequestBody StudentActivity studentActivity){
        StudentActivity completedActivity = studentService.completeActivity(studentId, studentActivity);
        return ResponseEntity.ok(completedActivity);
    }

}
