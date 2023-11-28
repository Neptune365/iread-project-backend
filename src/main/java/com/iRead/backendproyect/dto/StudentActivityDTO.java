package com.iRead.backendproyect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentActivityDTO {
    private int correctAnswer;
    private int incorrectAnswer;
    private int totalPoints;
}
