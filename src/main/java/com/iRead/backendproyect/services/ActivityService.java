package com.iRead.backendproyect.services;

import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.models.api_story.Activity;
import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.repositories.ActivityRepository;
import com.iRead.backendproyect.repositories.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final StoryRepository storyRepository;

    public Activity addActivityToStory(Long storyId, Activity activity) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        activity.setStory(story);

        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }
}
