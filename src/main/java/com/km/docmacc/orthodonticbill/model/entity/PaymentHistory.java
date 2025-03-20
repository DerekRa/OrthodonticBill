package com.km.docmacc.orthodonticbill.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.km.docmacc.orthodonticbill.model.dto.DateTimeCreated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "paymentHistory")
public class PaymentHistory extends DateTimeCreated {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paymentId", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;
    private Long transactionId;
    private Double paymentAmount;
    private String paymentNote;
    private String paymentReasonChange;
}
