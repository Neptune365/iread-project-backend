package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.StoryDTO;
import com.iRead.backendproyect.models.api_story.Activity;
import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.services.StoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Story", description = "Story management APIs")
@AllArgsConstructor
public class StoryController {

    private final StoryService storyService;

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

    @GetMapping("/byDetailsTeacher/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Story>> getDetailsStoriesByTeacherId(@PathVariable Long teacherId) {
        List<Story> stories = storyService.findAllDetailsStoriesByTeacherId(teacherId);
        return ResponseEntity.ok(stories);
    }

    @PutMapping("/activate/{storyId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public String activateStory(@PathVariable Long storyId) {
        return storyService.activateStory(storyId);
    }

    @PutMapping("/deactivate/{storyId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Map<String, Object>> deactivateStory(@PathVariable Long storyId) {
        Map<String, Object> response = storyService.deactivateStory(storyId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{storyId}/activity")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Story> assignActivityToStory(@PathVariable Long storyId, @RequestBody Activity activityDetails) {
        Story updatedStory = storyService.assignActivityToStory(storyId, activityDetails);
        return ResponseEntity.ok(updatedStory);
    }

    @DeleteMapping("/delete/{storyId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> deleteStory(@PathVariable Long storyId) {
        storyService.deleteStory(storyId);
        return ResponseEntity.ok("La historia con id: " + storyId + "se ha eliminado");
    }

}
