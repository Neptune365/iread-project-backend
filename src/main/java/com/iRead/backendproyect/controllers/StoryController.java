package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.services.StoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@Tag(name = "Story", description = "Story management APIs")
@AllArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping("/")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Story> listAllStories() {
        return storyService.listAllStories();
    }

    @PostMapping("/createStory/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Story> createStory(@PathVariable Long teacherId, @RequestBody Story story) {
        Story createdStory = storyService.createStoryForTeacher(teacherId, story);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @GetMapping("/byTeacher/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Story>> getStoriesByTeacherId(@PathVariable Long teacherId) {
        List<Story> stories = storyService.findAllStoriesByTeacherId(teacherId);
        return ResponseEntity.ok(stories);
    }
}
