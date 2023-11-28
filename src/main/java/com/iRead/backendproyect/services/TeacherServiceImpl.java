package com.iRead.backendproyect.services;

import com.iRead.backendproyect.config.Jwt.JwtService;
import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTORequest;
import com.iRead.backendproyect.dto.TeacherDTORequest;
import com.iRead.backendproyect.exception.EmailExistsException;
import com.iRead.backendproyect.exception.NoSuchElementException;
import com.iRead.backendproyect.mapper.TeacherMapper;
import com.iRead.backendproyect.models.Role;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.registration.token.ConfirmationToken;
import com.iRead.backendproyect.registration.token.ConfirmationTokenService;
import com.iRead.backendproyect.repositories.TeacherRepository;
import com.iRead.backendproyect.token.Token;
import com.iRead.backendproyect.token.TokenRepository;
import com.iRead.backendproyect.token.TokenType;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    public final TeacherRepository teacherRepository;
    public final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final TokenRepository tokenRepository;

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

        Long teacherID = user.getId();
        var extraClaims = new HashMap<String, Object>();

        var jwtToken = jwtService.generateToken(teacherID.toString(), extraClaims, user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthDTO.builder()
                .token(jwtToken).build();
    }

    private void revokeAllUserTokens(Teacher user) {
        var validUserTokens = tokenRepository.findAllValidTokensBy(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }

    private void saveUserToken(Teacher user, String jwtToken) {
        var token = Token.builder()
                .teacher(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public AuthDTO updateTeacher(Long teacherId, TeacherDTORequest teacherDTORequest) {
        Teacher teacherExists = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NoSuchElementException("Teacher not founded"));

        teacherMapper.updateModel(teacherDTORequest, teacherExists);

        teacherRepository.save(teacherExists);

        String newToken = jwtService.generateToken(teacherExists);

        return AuthDTO.builder()
                .token(newToken)
                .build();
    }


    public Optional<Teacher> findTeacherByEmail(String email) { return teacherRepository.findUserByEmail(email);}

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findTeacherById(teacherId);
    }

    public int enableUser(String email) {
        return teacherRepository.enableUser(email);
    }
}