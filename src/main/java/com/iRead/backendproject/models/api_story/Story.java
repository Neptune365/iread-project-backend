package com.iRead.backendproject.models.api_story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iRead.backendproject.models.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(min = 10, max = 25)
    private String title;

    @Column(name = "date_creation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dateCreation;

    @Column(name = "access_word", nullable = false)
    @Size(min = 5, max = 15)
    private String accessWord;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher teacher;

    @OneToMany(mappedBy = "story")
    @JsonIgnore
    private List<Rate> rates;

    @OneToMany(mappedBy = "story")
    private List<Interaction> activities;

    @PrePersist
    public void prePersist() {
        dateCreation = LocalDateTime.now();
    }

}
