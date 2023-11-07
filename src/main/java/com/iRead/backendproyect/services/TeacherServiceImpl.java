package com.iRead.backendproyect.services;

import com.iRead.backendproyect.config.Jwt.JwtService;
import com.iRead.backendproyect.config.validation.EmailValidator;
import com.iRead.backendproyect.dto.AuthDTO;
import com.iRead.backendproyect.dto.AuthenticationDTO;
import com.iRead.backendproyect.dto.TeacherDTO;
import com.iRead.backendproyect.email.EmailSender;
import com.iRead.backendproyect.exception.EmailExistsException;
import com.iRead.backendproyect.exception.MissingRequiredFieldsException;
import com.iRead.backendproyect.exception.NoSuchElementException;
import com.iRead.backendproyect.mapper.TeacherMapper;
import com.iRead.backendproyect.models.Teacher;
import com.iRead.backendproyect.repositories.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    public final TeacherRepository teacherRepository;
    public final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public AuthDTO register(TeacherDTO request) {
        if (request.getName() == null) throw new MissingRequiredFieldsException("name");
        if (request.getSurname() == null) throw new MissingRequiredFieldsException("surname");
        if (request.getEmail() == null) throw new MissingRequiredFieldsException("email");
        if (request.getPassword() == null)  throw new MissingRequiredFieldsException("password");

        if (teacherRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new EmailExistsException("Email ingresado ya existe");
        }

        if (!emailValidator.isValid(request.getEmail())){
            throw new IllegalStateException("Correo electrónico no válido");
        }

        Teacher user = teacherMapper.mapToModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        teacherRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        String link = "http://localhost:8080/api/auth/authenticate/";
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getName(), link));

        return AuthDTO.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthDTO authenticate(AuthenticationDTO request) {
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
    public AuthDTO updateTeacher(Long teacher_id, TeacherDTO teacherDTO) {
        Teacher teacherExists = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new NoSuchElementException("Teacher not founded"));

        teacherMapper.updateModel(teacherDTO, teacherExists);

        teacherRepository.save(teacherExists);

        String newToken = jwtService.generateToken(teacherExists);

        return AuthDTO.builder()
                .token(newToken)
                .build();
    }

    public Teacher findTeacherById(Long id) {
        return teacherRepository.findTeacherById(id);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}