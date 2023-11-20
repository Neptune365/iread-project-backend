package com.iRead.backendproyect.repositories;

import com.iRead.backendproyect.models.api_story.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByNameStudent(String nameStudent);
}
