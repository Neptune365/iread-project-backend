package com.iRead.backendproject.repositories;

import com.iRead.backendproject.models.api_story.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByNameStudent(String studentName);
}
