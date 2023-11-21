package com.iRead.backendproyect.services;

import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTO;
import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.models.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    AuthDTO register(TeacherDTO request);
    AuthDTO authenticate(AuthenticationDTO request);
    AuthDTO updateTeacher(Long teacher_id, TeacherDTO teacherDTO);
    Teacher findTeacherById(Long id);

}