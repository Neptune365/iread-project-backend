package com.iRead.backendproyect.services;

import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.models.api_story.Activity;
import com.iRead.backendproyect.models.api_story.StudentActivity;
import com.iRead.backendproyect.repositories.ActivityRepository;
import com.iRead.backendproyect.repositories.StudentActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final StudentActivityRepository studentActivityRepository;

    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }

    public Activity assignStudentActivityToActivity(Long activityId, Long studentActivityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + activityId));

        StudentActivity studentActivity = studentActivityRepository.findById(studentActivityId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentActivity not found with id: " + studentActivityId));

        studentActivity.setActivity(activity);
        studentActivityRepository.save(studentActivity);

        return activity;
    }
}

