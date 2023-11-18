package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.StoryDTO;
import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.mapper.StoryMapper;
import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.services.StoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stories")
@Tag(name = "Story", description = "Story management APIs")
@AllArgsConstructor
public class StoryController {

    private final StoryService storyService;
    private final StoryMapper storyMapper;

    @GetMapping("/")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<StoryDTO> listAllStories() {
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
    public ResponseEntity<List<StoryDTO>> getStoriesByTeacherId(@PathVariable Long teacherId) {
        List<StoryDTO> storyDTOS = storyService.findAllStoriesByTeacherId(teacherId);
        return ResponseEntity.ok(storyDTOS);
    }

    @PutMapping("/activate/{storyId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> activateStory(@PathVariable Long storyId) {
        try {
            storyService.activateStory(storyId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
