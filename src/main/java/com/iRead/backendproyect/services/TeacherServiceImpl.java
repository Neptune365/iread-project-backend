package com.iRead.backendproyect.services;

import com.iRead.backendproyect.config.Jwt.JwtService;
import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTORequest;
import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.dto.TeacherDTORequest;
import com.iRead.backendproyect.email.EmailSender;
import com.iRead.backendproyect.exception.EmailExistsException;
import com.iRead.backendproyect.exception.NoSuchElementException;
import com.iRead.backendproyect.mapper.TeacherMapper;
import com.iRead.backendproyect.models.Role;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.registration.token.ConfirmationToken;
import com.iRead.backendproyect.registration.token.ConfirmationTokenService;
import com.iRead.backendproyect.repositories.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    public final TeacherRepository teacherRepository;
    public final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public String singUpUser(Teacher user) {
        if (teacherRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistsException("Email ingresado ya existe");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        teacherRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    @Override
    public AuthDTO authenticate(AuthenticationDTORequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = teacherRepository.findUserByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthDTO.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthDTO updateTeacher(Long teacher_id, TeacherDTORequest teacherDTORequest) {
        Teacher teacherExists = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new NoSuchElementException("Teacher not founded"));

        teacherMapper.updateModel(teacherDTORequest, teacherExists);

        teacherRepository.save(teacherExists);

        String newToken = jwtService.generateToken(teacherExists);

        return AuthDTO.builder()
                .token(newToken)
                .build();
    }

    public Teacher findTeacherById(Long id) {
        return teacherRepository.findTeacherById(id);
    }

    public int enableUser(String email) {
        return teacherRepository.enableUser(email);
    }
}