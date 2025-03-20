package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long billId;
    private Double payment;
    private String note;
    private String createdByName;
    private String createdById;
}
