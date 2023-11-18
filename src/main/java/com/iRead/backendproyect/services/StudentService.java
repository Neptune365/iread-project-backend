package com.iRead.backendproyect.services;

import com.iRead.backendproyect.models.api_story.Student;
import com.iRead.backendproyect.repositories.StudentRepository;
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
