package com.iRead.backendproyect.models.api_stripe;

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
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id", nullable = false)
    private Long id;

    @Column(name = "type_plan", nullable = false)
    @Size(min = 10, max = 25)
    private String typePlan;

    @Column(name = "description", nullable = true)
    @Size(min = 30, max = 50)
    private String description;

    @Column(name = "duration", nullable = false)
    @Size(min = 20, max = 25)
    private String duration;

    @Column(name = "cost", nullable = false)
    private String cost;

    @OneToMany(mappedBy = "plan")
    private List<Subscription> subscriptions;

}
