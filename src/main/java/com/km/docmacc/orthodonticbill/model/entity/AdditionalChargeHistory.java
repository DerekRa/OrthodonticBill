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
@Table(name = "additionalChargeHistory")
public class AdditionalChargeHistory extends DateTimeCreated {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chargeId", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;
    private Long transactionId;
    private Double additionalChargeAmount;
    private String chargeReasonChange;
}
