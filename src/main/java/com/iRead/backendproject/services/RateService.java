package com.iRead.backendproject.services;

import com.iRead.backendproject.dto.RateDTO;
import com.iRead.backendproject.models.api_story.Rate;
import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.models.api_story.Student;
import com.iRead.backendproject.repositories.RateRepository;
import com.iRead.backendproject.repositories.StoryRepository;
import com.iRead.backendproject.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final StudentRepository studentRepository;
    private final StoryRepository storyRepository;

    public Rate addRate(RateDTO rateDTO) {
        Rate rate = new Rate();
        rate.setStars(rateDTO.getStars());

        Student student = studentRepository.findByNameStudent(rateDTO.getStudentName());
        Story story = storyRepository.findById(rateDTO.getStoryId())
                .orElse(null);

        if (student == null || story == null) {
            return null;
        }

        rate.setStudent(student);
        rate.setStory(story);

        return rateRepository.save(rate);

    }

}
