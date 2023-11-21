package com.iRead.backendproject.mapper;

import com.iRead.backendproject.dto.TeacherDTO;
import com.iRead.backendproject.models.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    private final ModelMapper modelMapper;

    public TeacherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Teacher mapToModel(TeacherDTO teacherDTO) {
        return modelMapper.map(teacherDTO, Teacher.class);
    }

    public void updateModel(TeacherDTO teacherDTO, Teacher teacher) {
        modelMapper.map(teacherDTO, teacher);
    }

    public TeacherDTO mapToDTO(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }

}
