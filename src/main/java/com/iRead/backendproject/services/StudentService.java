package com.iRead.backendproject.services;

import com.iRead.backendproject.exception.ResourceNotFoundException;
import com.iRead.backendproject.models.api_story.Student;
import com.iRead.backendproject.models.api_story.StudentActivity;
import com.iRead.backendproject.repositories.StudentActivityRepository;
import com.iRead.backendproject.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentActivityRepository studentActivityRepository;

    public Student enterName(Student student) {
        return  studentRepository.save(student);
    }

    public StudentActivity completeActivity(Long studentId, StudentActivity studentActivity) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        studentActivity.setStudent(student);
        return studentActivityRepository.save(studentActivity);
    }

}
