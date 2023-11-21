package com.iRead.backendproject.controllers;

import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.models.api_story.Student;
import com.iRead.backendproject.services.StoryService;
import com.iRead.backendproject.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StoryService storyService;

    @PostMapping("/")
    public ResponseEntity<Student> enterName(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.enterName(student));
    }

    @GetMapping("/access/{accessWord}")
    public ResponseEntity<Story> accessStoryByWordAccess(@PathVariable String accessWord) {
        Story story = storyService.findStoryAccessWord(accessWord);
        return ResponseEntity.ok(story);
    }

}
