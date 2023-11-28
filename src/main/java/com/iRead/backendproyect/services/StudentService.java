package com.iRead.backendproyect.services;

import com.iRead.backendproyect.dto.StoryDTORequest;
import com.iRead.backendproyect.dto.StoryResponse;
import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.models.Activity;
import com.iRead.backendproyect.models.Story;
import com.iRead.backendproyect.models.Student;
import com.iRead.backendproyect.models.StudentActivity;
import com.iRead.backendproyect.repositories.ActivityRepository;
import com.iRead.backendproyect.repositories.StoryRepository;
import com.iRead.backendproyect.repositories.StudentActivityRepository;
import com.iRead.backendproyect.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentActivityRepository studentActivityRepository;
    private final StoryRepository storyRepository;
    private final ActivityRepository activityRepository;

    public Student enterName(Student student) {
        return  studentRepository.save(student);
    }

    public StoryResponse accessStory(StoryDTORequest storyDTORequest) {
        Story story = storyRepository.findStoryByAccessWord(storyDTORequest.getAccessWord());
        if (story != null) {
            return new StoryResponse(story.getId(), story.getAccessWord(), story.getActive());
        } else {
            throw new IllegalStateException("No se puede acceder a la historia.");
        }
    }

    public StudentActivity completeActivity(Long studentId, StudentActivity studentActivity, Long activityId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Activity activity = activityRepository.findById(activityId)
                        .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + studentId));

        studentActivity.setStudent(student);
        studentActivity.setActivity(activity);
        return studentActivityRepository.save(studentActivity);
    }

}
