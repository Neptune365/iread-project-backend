package com.iRead.backendproyect.repositories;

import com.iRead.backendproyect.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findUserByEmail(String email);
    Teacher findTeacherById(Long teacherId);

    @Modifying
    @Query("UPDATE Teacher t SET t.enabled = true WHERE t.email = :email")
    int enableUser(String email);
}