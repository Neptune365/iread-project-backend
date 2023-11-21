package com.iRead.backendproject.models.api_story;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_interaction")
public class StudentInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_interaction_id", nullable = false)
    private Long id;

    @Column(name = "correct_answer", nullable = false)
    private boolean correctAnswer;

    @Column(name = "completion_time", nullable = false)
    private Duration completionTime;

    @Column(name = "consulted_word", nullable = false)
    @Size(min = 8, max = 25)
    private String consultedWord;


    @ManyToOne
    @JoinColumn(name = "interaction_id")
    private Interaction interaction;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
