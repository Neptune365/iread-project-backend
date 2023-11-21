package com.iRead.backendproject.services;

import com.iRead.backendproject.models.api_story.Student;
import com.iRead.backendproject.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student enterName(Student student) {
        return  studentRepository.save(student);
    }
}
