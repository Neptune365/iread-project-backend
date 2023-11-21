package com.iRead.backendproject.services;

import com.iRead.backendproject.dto.StoryDTO;
import com.iRead.backendproject.exception.ResourceNotFoundException;
import com.iRead.backendproject.mapper.StoryMapper;
import com.iRead.backendproject.models.Teacher;
import com.iRead.backendproject.models.api_story.Activity;
import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.models.api_story.StudentActivity;
import com.iRead.backendproject.repositories.*;
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
    private final StudentService studentService;

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
