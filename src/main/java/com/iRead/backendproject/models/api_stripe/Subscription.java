package com.iRead.backendproject.models.api_stripe;

import com.iRead.backendproject.models.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suscription_id")
    private Long id;

    @Column(name = "start_subscription", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startSubscription;

    @Column(name = "end_subscription", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime endSubscription;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @PrePersist
    public void prePersist() {
        startSubscription = LocalDateTime.now();
    }

}
