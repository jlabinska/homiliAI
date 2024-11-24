package com.homiliai.core.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateSermonDTO {
    private LocalDate sermonDate; // Selected date
    private String audience; // Selected audience
    private Long occasionId; // Selected occasion ID
    private String content; // Sermon content
}