package com.iRead.backendproject.services;

import com.iRead.backendproject.dto.RateDTORequest;
import com.iRead.backendproject.exception.ResourceNotFoundException;
import com.iRead.backendproject.models.api_story.Rate;
import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.repositories.RateRepository;
import com.iRead.backendproject.repositories.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
