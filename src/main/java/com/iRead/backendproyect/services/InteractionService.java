package com.iRead.backendproyect.services;

import com.iRead.backendproyect.models.api_story.Interaction;
import com.iRead.backendproyect.repositories.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;


}
