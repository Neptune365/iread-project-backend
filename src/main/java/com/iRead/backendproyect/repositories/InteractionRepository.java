package com.iRead.backendproyect.repositories;

import com.iRead.backendproyect.models.api_story.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
}
