package com.iRead.backendproject.repositories;

import com.iRead.backendproject.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findUserByEmail(String email);
    Teacher findTeacherById(Long id);
}