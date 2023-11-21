package com.iRead.backendproyect.services;

import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.models.api_story.Student;
import com.iRead.backendproyect.models.api_story.StudentActivity;
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

    public Student enterName(Student student) {
        return  studentRepository.save(student);
    }

    public boolean accessStory(Long studentId, String providedAccessWord) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Story story = storyRepository.findStoryByAccessWord(providedAccessWord);

        return story != null && story.getAccessWord().equals(providedAccessWord);
    }

    public StudentActivity completeActivity(Long studentId, StudentActivity studentActivity, String providedAccessWord) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        if (accessStory(studentId, providedAccessWord)) {
            Story story = storyRepository.findStoryByAccessWord(providedAccessWord);
            if (story != null && story.getActive()) {
                studentActivity.setStudent(student);
                return studentActivityRepository.save(studentActivity);
            } else {
                throw new IllegalStateException("No se puede completar la actividad. Acceso denegado o historia inactiva.");
            }
        } else {
            throw new IllegalStateException("No se puede completar la actividad. Acceso denegado a la historia.");
        }
    }

}
