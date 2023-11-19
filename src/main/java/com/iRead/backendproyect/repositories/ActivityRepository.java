package com.iRead.backendproyect.repositories;

import com.iRead.backendproyect.models.api_story.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
