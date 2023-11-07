package com.iRead.backendproyect.repositories;

import com.iRead.backendproyect.models.api_story.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
