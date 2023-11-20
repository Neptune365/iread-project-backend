package com.iRead.backendproyect.services;

import com.iRead.backendproyect.dto.RateDTORequest;
import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.models.api_story.Rate;
import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.repositories.RateRepository;
import com.iRead.backendproyect.repositories.StoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final StoryRepository storyRepository;

    public Rate addRate(Long storyId, RateDTORequest rateDTORequest) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la story con id: " + storyId));

        Rate rate = new Rate();
        rate.setStars(rateDTORequest.getStars());
        rate.setStory(story);

        return rateRepository.save(rate);
    }

}
