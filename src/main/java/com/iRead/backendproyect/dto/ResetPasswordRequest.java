package com.iRead.backendproyect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Por favor agrega un correo")
    private String email;
    @NotBlank(message = "Por favor ingresa el token")
    private String recoveryToken;
    @NotBlank(message = "Por favor agrega nueva contraseña")
    private String newPassword;
    @NotBlank(message = "Por favor confirma la nueva contraseña")
    private String confirmationPassword;


}
