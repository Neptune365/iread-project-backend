package com.iRead.backendproject.services;

import com.iRead.backendproject.dto.AuthDTO;
import com.iRead.backendproject.dto.AuthenticationDTO;
import com.iRead.backendproject.dto.TeacherDTO;
import com.iRead.backendproject.models.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    AuthDTO register(TeacherDTO request);
    AuthDTO authenticate(AuthenticationDTO request);
    AuthDTO updateTeacher(Long teacher_id, TeacherDTO teacherDTO);
    Teacher findTeacherById(Long id);

}