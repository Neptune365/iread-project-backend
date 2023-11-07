package com.iRead.backendproyect.models.api_story;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "interaction")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id", nullable = false)
    private Long id;

    @Column(name = "interaction_duration", nullable = false)
    private Duration interactionDuration;

    @Column(name = "quiz_json", nullable = false)
    private String quizJson;

    @Column(name = "tale_json", nullable = false)
    private String taleJson;

    @Column(name = "vocabulary_json", nullable = false)
    private String vocabularyJson;


    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @OneToMany(mappedBy = "interaction")
    private List<StudentInteraction> studentInteractions;

}
