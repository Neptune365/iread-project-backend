package com.iRead.backendproyect.dto;

import com.iRead.backendproyect.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    @NotBlank(message = "Por favor agrega un nombre")
    @Size(max = 25)
    private String name;
    @NotBlank(message = "Por favor agrega los apellidos")
    @Size(max = 25)
    private String surname;
    @NotBlank(message = "Por favor agrega un correo")
    @Size(max = 40)
    @Email
    private String email;
    @NotBlank(message = "Por favor agrega una contraseña")
    @Size(min = 8, max = 25, message = "La contraseña debe tener entre 8 y 25 caracteres.")
    private String password;

    @Builder.Default
    private Role role = Role.USER;

}
