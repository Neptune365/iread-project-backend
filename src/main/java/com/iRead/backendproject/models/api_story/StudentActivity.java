package com.iRead.backendproject.models.api_story;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "student_activity")
public class StudentActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_interaction_id", nullable = false)
    private Long id;

    @Column(name = "correct_answer", nullable = false)
    private int correctAnswer;

//    @Column(name = "completion_time", nullable = false)
//    private Duration completionTime;

    @Column(name = "activity_json", nullable = false, columnDefinition = "TEXT")
    private String activityJson;

    @Column(name = "consulted_word", nullable = false)
//    @Size(min = 8, max = 25)
    private int consultedWord;


    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonIgnore
    private Activity activity;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "student_id")
    private Student student;

}
