package com.km.docmacc.orthodonticbill.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Double paymentAmount;
    private String paymentNote;
    private String paymentReasonChange;
    private String createdByName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
}
