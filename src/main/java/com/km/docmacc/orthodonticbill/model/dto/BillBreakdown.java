package com.km.docmacc.orthodonticbill.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillBreakdown {
    private Long id;
    private Double totalBill;
    private Double additionalChargeAmount;
    private Double paymentAmount;
    private String paymentNote;
    private String transactionType;
    private Double totalAmountPaid;
    private Double balance;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private LocalDateTime createdDateTime;
}
