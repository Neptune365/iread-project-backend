package com.iRead.backendproyect.controllers;

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

    @PutMapping("/{activityId}/studentActivities/{studentActivityId}")
    public ResponseEntity<Activity> assignStudentActivityToActivity(@PathVariable Long activityId, @PathVariable Long studentActivityId) {
        Activity updatedActivity = activityService.assignStudentActivityToActivity(activityId, studentActivityId);
        return ResponseEntity.ok(updatedActivity);
    }

}
