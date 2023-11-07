package com.iRead.backendproyect.dto;

import lombok.Data;

@Data
public class RateDTO {
    private Long storyId;
    private int stars;
    private String studentName;
}
