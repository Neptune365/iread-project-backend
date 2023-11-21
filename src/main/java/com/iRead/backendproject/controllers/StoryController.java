package com.iRead.backendproject.controllers;

import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.services.StoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@Tag(name = "Story", description = "Story management APIs")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping("/")
    public List<Story> listAllStories() {
        return storyService.listAllStories();
    }

    @PostMapping("/createStory/{teacherId}")
    public ResponseEntity<Story> createStory(@PathVariable Long teacherId, @RequestBody Story story) {
        Story createdStory = storyService.createStoryForTeacher(teacherId, story);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @GetMapping("/byTeacher/{teacherId}")
    public ResponseEntity<List<Story>> getStoriesByTeacherId(@PathVariable Long teacherId) {
        List<Story> stories = storyService.findAllStoriesByTeacherId(teacherId);
        return ResponseEntity.ok(stories);
    }
}
