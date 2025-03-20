package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUpdateRequest {
    private Long paymentTransactionId;
    private Double payment;
    private String note;
    private String reasonChange;
    private String createdByName;
    private String createdById;
}
