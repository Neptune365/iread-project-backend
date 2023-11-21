package com.iRead.backendproject.services;

import com.iRead.backendproject.exception.ResourceNotFoundException;
import com.iRead.backendproject.models.Teacher;
import com.iRead.backendproject.models.api_story.Story;
import com.iRead.backendproject.repositories.StoryRepository;
import com.iRead.backendproject.repositories.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final TeacherRepository teacherRepository;

    public List<Story> listAllStories() {
        return storyRepository.findAll();
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

    public List<Story> findAllStoriesByTeacherId(Long teacherId) {
        return storyRepository.findAllStoriesByTeacherId(teacherId);
    }

    public Story findStoryAccessWord(String accessWord) throws ResourceNotFoundException {
        Story story = storyRepository.findStoryModelByAccessWord(accessWord);
        if (story == null) {
            throw new ResourceNotFoundException("No se encontró una historia con la palabra de acceso: " + accessWord);
        }
        return story;
    }


}
