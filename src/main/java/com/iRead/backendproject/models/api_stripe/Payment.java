package com.iRead.backendproject.models.api_stripe;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(name = "transsaction_code", nullable = false)
    private String transsactionCode;

    @Column(name = "card_id", nullable = false)
    private String cardId;

    @Column(name = "owner_name", nullable = false)
    @Size(min = 15, max = 25)
    private String ownerName;


    @OneToOne(mappedBy = "payment")
    private Subscription subscription;



}
