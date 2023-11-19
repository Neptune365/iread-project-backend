package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.models.api_story.Activity;
import com.iRead.backendproyect.services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/")
    public List<Activity> getAllInt() {
        return activityService.getAllActivities();
    }

    @PostMapping("/{storyId}")
    public ResponseEntity<Activity> createInteraction(@PathVariable Long storyId, @RequestBody Activity activity) throws ResourceNotFoundException {
        Activity createdActivity = activityService.addActivityToStory(storyId, activity);
        return ResponseEntity.ok(createdActivity);
    }

}
