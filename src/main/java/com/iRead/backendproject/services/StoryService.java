package com.iRead.backendproject.services;

import com.iRead.backendproject.dto.StoryDTO;
import com.iRead.backendproject.exception.ResourceNotFoundException;
import com.iRead.backendproject.mapper.StoryMapper;
import com.iRead.backendproject.models.Teacher;
import com.iRead.backendproject.models.api_story.Activity;
import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final TeacherRepository teacherRepository;
    private final StoryMapper storyMapper;
    private final ActivityRepository activityRepository;
    private final StudentActivityRepository studentActivityRepository;
    private final StudentRepository studentRepository;

    public List<StoryDTO> listAllStories() {
        List<Story> stories = storyRepository.findAll();

        return stories.stream()
                .map(storyMapper::mapToDTO)
                .collect(Collectors.toList());

    }

    public Story createStoryForTeacher(Long teacherId, Story story) throws ResourceNotFoundException {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);

        if (teacher != null) {
            story.setTeacher(teacher);
            story.prePersist();
            return storyRepository.save(story);

        } else {
            throw new ResourceNotFoundException("No se encontró al profesor con el ID: " + teacherId);
        }
    }

    public List<StoryDTO> findAllStoriesByTeacherId(Long teacherId) {
        List<Story> stories = storyRepository.findAllStoriesByTeacherId(teacherId);

        return stories.stream()
                .map(storyMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<Story> findAllDetailsStoriesByTeacherId(Long teacherId) {
        List<Story> stories = storyRepository.findAllStoriesByTeacherId(teacherId);

        return stories;
    }

    public Map<String, String> activateStory(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        story.setActive(true);
        storyRepository.save(story);

        Map<String, String> response = new HashMap<>();
        response.put("message", "La historia ha comenzado");
        response.put("title", story.getTitle());

        return response;
    }

    public boolean findStoryByAccessWord(String accessWord) throws ResourceNotFoundException, IllegalStateException {
        Story story = storyRepository.findStoryByAccessWord(accessWord);

        if (story == null) {
            throw new ResourceNotFoundException("No se encontró una historia con la palabra de acceso: " + accessWord);
        }

        if (!story.getActive()) {
            throw new IllegalStateException("La historia no está activa. No se puede acceder a ella.");
        } else {
            System.out.println("Ingresaste a la historia.");
            return true;
        }
    }

    public Story assignActivityToStory(Long storyId, Activity activityDetails) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        Activity newActivity = new Activity();
        newActivity.setJsonConverted(activityDetails.getJsonConverted());

        newActivity.setStory(story);
        activityRepository.save(newActivity);

        story.setActivities(newActivity);
        storyRepository.save(story);

        return story;
    }

    @Transactional
    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }
}
