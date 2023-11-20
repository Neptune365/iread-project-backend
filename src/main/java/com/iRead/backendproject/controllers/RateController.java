package com.iRead.backendproject.controllers;

import com.iRead.backendproject.dto.RateDTORequest;
import com.iRead.backendproject.models.api_story.Rate;
import com.iRead.backendproject.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/add/{storyId}")
    public ResponseEntity<Rate> addRateToStory(@PathVariable Long storyId, @RequestBody RateDTORequest rateDTORequest) {
        Rate rate = rateService.addRate(storyId, rateDTORequest);
        return ResponseEntity.ok(rate);
    }

}
