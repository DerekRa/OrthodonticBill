package com.km.docmacc.orthodonticbill.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.DATE_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrthoBillDataResponse {
    private Long id;
    private String billName;
    private Double totalBill;
    private Double totalBalance;
    private Double totalAmountPaid;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate createdDate;

    public boolean equalsBillName(String findItem) {
        return this.billName.toLowerCase().contains(findItem.toLowerCase());
    }

    public boolean equalsTotalBill(String findItem) {
        try {
            Double totalBill = Double.parseDouble(findItem);
            return this.totalBill.equals(totalBill);
        } catch (NumberFormatException e){
            return false;
        }
    }
    public boolean equalsTotalBalance(String findItem) {
        try {
            Double balanceBill = Double.parseDouble(findItem);
            return this.totalBalance.equals(balanceBill);
        } catch (NumberFormatException e){
            return false;
        }
    }
}
