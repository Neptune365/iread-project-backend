package com.iRead.backendproject.repositories;

import com.iRead.backendproject.models.api_story.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}