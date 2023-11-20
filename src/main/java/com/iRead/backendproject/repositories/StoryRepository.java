package com.iRead.backendproject.repositories;

import com.iRead.backendproject.models.api_story.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {
    Story findStoryByAccessWord(String accessWord);
    List<Story> findAllStoriesByTeacherId(Long id);
}
