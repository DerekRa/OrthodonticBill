package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrthoBillDataChangeRequest {
    private Long billId;
    private String billName;
    private Double totalBill;
    private String reasonChanged;
    private String createdByName;
    private String createdById;
}
