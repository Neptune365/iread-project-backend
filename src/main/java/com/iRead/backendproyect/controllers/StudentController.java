package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.models.api_story.Student;
import com.iRead.backendproyect.services.StoryService;
import com.iRead.backendproyect.services.StudentService;
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
