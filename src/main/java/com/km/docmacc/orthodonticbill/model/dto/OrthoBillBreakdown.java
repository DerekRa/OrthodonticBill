package com.km.docmacc.orthodonticbill.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrthoBillBreakdown {
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
