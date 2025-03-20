package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalChargeUpdateRequest {
    private Long chargeTransactionId;
    private Double additionalChargeAmount;
    private String reasonChange;
    private String createdByName;
    private String createdById;
}
