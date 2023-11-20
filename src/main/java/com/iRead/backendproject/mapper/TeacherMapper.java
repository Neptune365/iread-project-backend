package com.iRead.backendproject.mapper;

import com.iRead.backendproject.dto.TeacherDTO;
import com.iRead.backendproject.dto.TeacherDTORequest;
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

    public TeacherDTO mapToDTO(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public void updateModel(TeacherDTORequest teacherDTORequest, Teacher teacher) {
        modelMapper.map(teacherDTORequest, teacher);
    }

}
