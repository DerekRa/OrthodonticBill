package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillsTotalName {
    private String billName;
    private Double totalBill;
    private Double totalBalance;
    private Double totalPayment;
    private Double totalAdditionalCharge;
}
