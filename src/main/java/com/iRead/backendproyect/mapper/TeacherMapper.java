package com.iRead.backendproyect.mapper;

import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.dto.TeacherDTORequest;
import com.iRead.backendproyect.models.Teacher;
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
