package com.km.docmacc.orthodonticbill.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.DATE_FORMAT;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class DateTimeCreated {
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate createdDate;
    private LocalDateTime createdDateTime;
    private String createdByName;
    private String createdById;
}
