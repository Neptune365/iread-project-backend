package com.iRead.backendproject.services;

import com.iRead.backendproject.dto.AuthDTO;
import com.iRead.backendproject.dto.AuthenticationDTORequest;
import com.iRead.backendproject.dto.TeacherDTORequest;
import com.iRead.backendproject.models.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    String singUpUser(Teacher teacher);
    AuthDTO authenticate(AuthenticationDTORequest request);
    AuthDTO updateTeacher(Long teacherId, TeacherDTORequest teacherDTORequest);
    Teacher findTeacherById(Long teacherId);

}