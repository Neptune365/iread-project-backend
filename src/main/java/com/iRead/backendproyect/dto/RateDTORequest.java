package com.iRead.backendproyect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateDTORequest {
    @NotBlank(message = "Por favor ingresa un correo electrónico.")
    private int stars;
}
