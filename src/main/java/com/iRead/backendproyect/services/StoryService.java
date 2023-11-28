package com.iRead.backendproyect.services;

import com.iRead.backendproyect.dto.StoryDTO;
import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.mapper.StoryMapper;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.models.Activity;
import com.iRead.backendproyect.models.Story;
import com.iRead.backendproyect.models.StudentActivity;
import com.iRead.backendproyect.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final TeacherRepository teacherRepository;
    private final StoryMapper storyMapper;
    private final ActivityRepository activityRepository;

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

    public String activateStory(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
            .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        story.setActive(true);
        storyRepository.save(story);

        return story.getTitle();
    }

    public Map<String, Object> deactivateStory(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        story.setActive(false);
        storyRepository.save(story);

        List<StudentActivity> studentActivities = story.getActivities().getStudentActivities();
        List<Map<String, Object>> studentDetails = new ArrayList<>();

        for (StudentActivity studentActivity : studentActivities) {
            Map<String, Object> details = new HashMap<>();
            details.put("nameStudent", studentActivity.getStudent().getNameStudent());
            details.put("correctAnswer", studentActivity.getCorrectAnswer());
            studentDetails.add(details);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("title", story.getTitle());
        response.put("students", studentDetails);
        response.put("totalStudents", studentActivities.size());

        return response;
    }

    public Story assignActivityToStory(Long storyId, Activity activityDetails) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Historia no encontrada: " + storyId));

        Activity newActivity = new Activity();
        newActivity.setJsonConverted(activityDetails.getJsonConverted());
        newActivity.setImgPreview(activityDetails.getImgPreview());

        newActivity.setStory(story);
        activityRepository.save(newActivity);

        story.setActivities(newActivity);
        storyRepository.save(story);

        return story;
    }

    public Activity getActivityByStoryId(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        return story.getActivities();
    }

    @Transactional
    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }
}
