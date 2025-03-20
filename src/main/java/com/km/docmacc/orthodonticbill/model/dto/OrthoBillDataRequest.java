package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrthoBillDataRequest {
    private Long profileId;
    private String billName;
    private Double totalBill;
    private String createdByName;
    private String createdById;
}
