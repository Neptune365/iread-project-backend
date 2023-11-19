package com.iRead.backendproyect.models.api_story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Long id;

    @Column(name = "name_student", nullable = false)
    @Size(min = 5, max = 30)
    private String nameStudent;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Rate> rates;

//    @OneToMany(mappedBy = "student")
//    private List<StudentInteraction> studentActivities;

}
