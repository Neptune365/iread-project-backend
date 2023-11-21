package com.iRead.backendproject.controllers;

import com.iRead.backendproject.dto.RateDTO;
import com.iRead.backendproject.models.api_story.Rate;
import com.iRead.backendproject.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/submit")
    public Rate submitRating(@RequestBody RateDTO rateDTO) {
        return rateService.addRate(rateDTO);
    }

}