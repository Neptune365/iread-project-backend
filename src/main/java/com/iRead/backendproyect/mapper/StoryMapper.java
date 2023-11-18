package com.iRead.backendproyect.mapper;

import com.iRead.backendproyect.dto.StoryDTO;
import com.iRead.backendproyect.models.api_story.Story;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StoryMapper {

    private final ModelMapper modelMapper;

    public StoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StoryDTO mapToDTO(Story story) {
        return modelMapper.map(story, StoryDTO.class);
    }

    public Story mapToModel(StoryDTO storyDTO) {
        return modelMapper.map(storyDTO, Story.class);
    }

}
