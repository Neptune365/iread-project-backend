package com.iRead.backendproyect.models.api_story;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id", nullable = false)
    private Long id;

    @Column(name = "stars", nullable = false)
    @Min(0)
    @Max(5)
    private int stars;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

}