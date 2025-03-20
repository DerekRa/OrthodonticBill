package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalChargeRequest {
    private Long billId;
    private Double additionalChargeAmount;
    private String createdByName;
    private String createdById;
}
