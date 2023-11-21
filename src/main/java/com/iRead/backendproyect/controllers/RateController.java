package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.RateDTORequest;
import com.iRead.backendproyect.models.api_story.Rate;
import com.iRead.backendproyect.services.RateService;
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
