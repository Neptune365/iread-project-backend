package com.iRead.backendproyect.repositories;

import com.iRead.backendproyect.models.StudentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {
}
