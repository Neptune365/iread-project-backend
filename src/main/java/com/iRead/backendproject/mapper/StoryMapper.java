package com.iRead.backendproject.mapper;

import com.iRead.backendproject.dto.StoryDTO;
import com.iRead.backendproject.models.api_story.Story;
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
