package com.km.docmacc.orthodonticbill.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrthoBillDataChangeResponse {
    private String billName;
    private Double totalBill;
    private String reasonChanged;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private String createdByName;
}
