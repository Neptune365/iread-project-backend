package com.iRead.backendproject.models.api_story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id", nullable = false)
    private Long id;

    @Column(name = "json_converted", nullable = false, columnDefinition = "TEXT")
    private String jsonConverted;

    @OneToOne
    @JoinColumn(name = "story_id")
    @JsonIgnore
    private Story story;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE)
    private List<StudentActivity> studentActivities;

}