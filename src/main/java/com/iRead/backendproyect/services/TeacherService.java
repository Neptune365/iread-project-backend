package com.iRead.backendproyect.services;

import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTORequest;
import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.dto.TeacherDTORequest;
import com.iRead.backendproyect.models.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    TeacherDTO register(TeacherDTORequest request);
    AuthDTO authenticate(AuthenticationDTORequest request);
    AuthDTO updateTeacher(Long teacher_id, TeacherDTORequest teacherDTORequest);
    Teacher findTeacherById(Long id);

}