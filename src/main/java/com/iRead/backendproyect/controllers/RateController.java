package com.iRead.backendproyect.controllers;

import com.iRead.backendproyect.dto.RateDTO;
import com.iRead.backendproyect.models.api_story.Rate;
import com.iRead.backendproyect.services.RateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rates")
@AllArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/submit")
    public Rate submitRating(@RequestBody RateDTO rateDTO) {
        return rateService.addRate(rateDTO);
    }

}