package com.iRead.backendproyect.services;

import com.iRead.backendproyect.dto.StoryDTO;
import com.iRead.backendproyect.exception.ResourceNotFoundException;
import com.iRead.backendproyect.mapper.StoryMapper;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.models.api_story.Story;
import com.iRead.backendproyect.repositories.StoryRepository;
import com.iRead.backendproyect.repositories.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final TeacherRepository teacherRepository;
    private final StoryMapper storyMapper;

    public List<StoryDTO> listAllStories() {
        List<Story> stories = storyRepository.findAll();

        return stories.stream()
                .map(storyMapper::mapToDTO)
                .collect(Collectors.toList());

    }

    public Story createStoryForTeacher(Long teacherId, Story story) throws ResourceNotFoundException {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);

        if (teacher != null) {
            story.setTeacher(teacher);
            story.prePersist();
            return storyRepository.save(story);

        } else {
            throw new ResourceNotFoundException("No se encontró al profesor con el ID: " + teacherId);
        }
    }

    public List<StoryDTO> findAllStoriesByTeacherId(Long teacherId) {
        List<Story> stories = storyRepository.findAllStoriesByTeacherId(teacherId);

        return stories.stream()
                .map(storyMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public void activateStory(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        story.setActive(true);

        storyRepository.save(story);
    }

    public void findStoryByAccessWord(String accessWord) throws ResourceNotFoundException, IllegalStateException {
        Story story = storyRepository.findStoryByAccessWord(accessWord);

        if (story == null) {
            throw new ResourceNotFoundException("No se encontró una historia con la palabra de acceso: " + accessWord);
        }

        if (!story.getActive()) {
            throw new IllegalStateException("La historia no está activa. No se puede acceder a ella.");
        } else {
            System.out.println("Ingresaste a la historia.");
        }
    }


}
