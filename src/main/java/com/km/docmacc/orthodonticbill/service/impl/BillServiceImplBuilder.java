package com.km.docmacc.orthodonticbill.service.impl;

import com.km.docmacc.orthodonticbill.model.dto.*;
import com.km.docmacc.orthodonticbill.model.entity.*;
import com.km.docmacc.orthodonticbill.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.*;

@Slf4j
public class BillServiceImplBuilder extends BuildResponseEntity {
    @Autowired
    private OrthoBillRepository orthoBillRepository;
    @Autowired
    private BillChangedHistoryRepository billChangedHistoryRepository;
    @Autowired
    private PayOrChargeTransactionRepository payOrChargeTransactionRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private AdditionalChargeHistoryRepository additionalChargeHistoryRepository;
    protected OrthoBill buildOrthoBill(OrthoBillDataRequest dataRequest) {
        return OrthoBill.builder()
                .billName(dataRequest.getBillName())
                .totalBill(dataRequest.getTotalBill())
                .profileId(dataRequest.getProfileId())
                .createdDate(LocalDate.now())
                .createdDateTime(LocalDateTime.now())
                .createdById(dataRequest.getCreatedById())
                .createdByName(dataRequest.getCreatedByName())
                .build();
    }
    protected BillChangedHistory buildBillChangedHistory(OrthoBillDataChangeRequest dataChangeRequest) {
        return BillChangedHistory.builder()
                .orthoBillId(dataChangeRequest.getBillId())
                .billName(dataChangeRequest.getBillName())
                .totalBill(dataChangeRequest.getTotalBill())
                .reasonChanged(dataChangeRequest.getReasonChanged())
                .createdById(dataChangeRequest.getCreatedById())
                .createdByName(dataChangeRequest.getCreatedByName())
                .createdDate(LocalDate.now())
                .createdDateTime(LocalDateTime.now())
                .build();
    }
    protected PayOrChargeTransaction buildAdditionalChargeTransaction(AdditionalChargeRequest dataRequest) {
        return PayOrChargeTransaction.builder()
                .orthoBillId(dataRequest.getBillId())
                .additionalChargeAmount(dataRequest.getAdditionalChargeAmount())
                .paymentAmount(0.0)
                .transactionType("AdditionalCharge")
                .createdById(dataRequest.getCreatedById())
                .createdByName(dataRequest.getCreatedByName())
                .createdDate(LocalDate.now())
                .createdDateTime(LocalDateTime.now())
                .build();
    }
    protected PayOrChargeTransaction buildPaymentTransaction(PaymentRequest dataRequest) {
        return PayOrChargeTransaction.builder()
                .orthoBillId(dataRequest.getBillId())
                .additionalChargeAmount(0.0)
                .paymentAmount(dataRequest.getPayment())
                .paymentNote(dataRequest.getNote())
                .transactionType("Payment")
                .createdById(dataRequest.getCreatedById())
                .createdByName(dataRequest.getCreatedByName())
                .createdDate(LocalDate.now())
                .createdDateTime(LocalDateTime.now())
                .build();
    }
    protected AdditionalChargeHistory buildAdditionalChargeHistory(AdditionalChargeUpdateRequest dataRequest) {
        return AdditionalChargeHistory.builder()
                .transactionId(dataRequest.getChargeTransactionId())
                .additionalChargeAmount(dataRequest.getAdditionalChargeAmount())
                .chargeReasonChange(dataRequest.getReasonChange())
                .createdById(dataRequest.getCreatedById())
                .createdByName(dataRequest.getCreatedByName())
                .createdDate(LocalDate.now())
                .createdDateTime(LocalDateTime.now())
                .build();
    }
    protected PaymentHistory buildPaymentHistoryTransaction(PaymentUpdateRequest dataRequest) {
        return PaymentHistory.builder()
                .transactionId(dataRequest.getPaymentTransactionId())
                .paymentAmount(dataRequest.getPayment())
                .paymentNote(dataRequest.getNote())
                .paymentReasonChange(dataRequest.getReasonChange())
                .createdById(dataRequest.getCreatedById())
                .createdByName(dataRequest.getCreatedByName())
                .createdDate(LocalDate.now())
                .createdDateTime(LocalDateTime.now())
                .build();
    }
    // Pagination Total Balance
    protected List<OrthoBillDataResponse> buildTotalBalanceCustomPagination(List<OrthoBillDataResponse> orthoBillDataResponses, PaginationData paginationData) {
        List<OrthoBillDataResponse> orthoBillSortOrder = buildTotalBalanceCustomSortOrder(orthoBillDataResponses, paginationData);
        return buildTotalBalancePaginationPage(orthoBillSortOrder, paginationData);
    }
    private List<OrthoBillDataResponse> buildTotalBalanceCustomSortOrder(List<OrthoBillDataResponse> orthoBillDataResponses, PaginationData paginationData) {
        if(paginationData.getOrderBy().equalsIgnoreCase(ASC)){
            orthoBillDataResponses.sort(Comparator.comparing(OrthoBillDataResponse::getTotalBalance));
        } else {
            orthoBillDataResponses.sort(Comparator.comparing(OrthoBillDataResponse::getTotalBalance).reversed());
        }
        return orthoBillDataResponses;
    }
    private List<OrthoBillDataResponse> buildTotalBalancePaginationPage(List<OrthoBillDataResponse> newOrthoBills, PaginationData paginationData){
        List<OrthoBillDataResponse> orthoBills = new ArrayList<>();
        int indexLoop = 0;
        int countReturn = 0;
        int pageNo = paginationData.getPageNo() > 0 ? paginationData.getPageNo()*paginationData.getPageSize() : paginationData.getPageNo();
        for (OrthoBillDataResponse orthoBill: newOrthoBills){
            if(paginationData.getSortBy().equalsIgnoreCase(TOTAL_BALANCE)){
                OrthoBillDataResponse billConverted = new OrthoBillDataResponse();
                BeanUtils.copyProperties(orthoBill, billConverted);
                if(billConverted.equalsTotalBalance(paginationData.getFindItem())){
                    List<Integer> increment = billTotalBalanceIncrementLoop(countReturn, indexLoop, pageNo, paginationData.getPageSize(), orthoBills, orthoBill);
                    countReturn = increment.get(0);
                    indexLoop = increment.get(1);
                }
            }
        }
        return orthoBills;
    }
    private List<Integer> billTotalBalanceIncrementLoop(int countReturn, int indexLoop, int pageNo, Integer pageSize, List<OrthoBillDataResponse> orthoBills, OrthoBillDataResponse orthoBill) {
        if(indexLoop >= pageNo && countReturn <= pageSize){
            orthoBills.add(orthoBill);
            countReturn++;
        }
        indexLoop++;
        return Arrays.asList(countReturn, indexLoop);
    }
    // Pagination for Bill Name and Total Bill
    protected List<OrthoBill> buildBillCustomPagination(PaginationData paginationData) {
        List<OrthoBill> orthoBills = orthoBillRepository.findByProfileId(paginationData.getId());
        List<OrthoBill> newOrthoBills = new ArrayList<>();
        for (OrthoBill orthoBill: orthoBills){
            String billName = orthoBill.getBillName();
            double totalBill = orthoBill.getTotalBill();
            BillLatest billLatest = getBillLatest(orthoBill, totalBill, billName);
            orthoBill.setBillName(billLatest.getBillName());
            orthoBill.setTotalBill(billLatest.getTotalBill());
            newOrthoBills.add(orthoBill);
        }
        List<OrthoBill> orthoBillSortOrder = buildBillCustomSortOrder(newOrthoBills, paginationData);
        return buildBillPaginationPage(orthoBillSortOrder, paginationData);
    }
    private List<OrthoBill> buildBillCustomSortOrder(List<OrthoBill> orthoBills, PaginationData paginationData) {
        if(paginationData.getSortBy().equalsIgnoreCase(SEARCH_ALL_COLUMNS)){// sort by date as default
            if(paginationData.getOrderBy().equalsIgnoreCase(ASC)){
                orthoBills.sort(Comparator.comparing(OrthoBill::getCreatedDateTime));
            } else {
                orthoBills.sort(Comparator.comparing(OrthoBill::getCreatedDateTime).reversed());
            }
        } else {//column to sort
            switch (paginationData.getSortBy()) {
                case BILL_NAME:
                    if(paginationData.getOrderBy().equalsIgnoreCase(ASC)){
                        orthoBills.sort(Comparator.comparing(OrthoBill::getBillName));
                    } else {
                        orthoBills.sort(Comparator.comparing(OrthoBill::getBillName).reversed());
                    }
                    break;
                case TOTAL_BILL:
                    if(paginationData.getOrderBy().equalsIgnoreCase(ASC)){
                        orthoBills.sort(Comparator.comparing(OrthoBill::getTotalBill));
                    } else {
                        orthoBills.sort(Comparator.comparing(OrthoBill::getTotalBill).reversed());
                    }
                    break;
                default:
                    if(paginationData.getOrderBy().equalsIgnoreCase(ASC)){
                        orthoBills.sort(Comparator.comparing(OrthoBill::getCreatedDateTime));
                    } else {
                        orthoBills.sort(Comparator.comparing(OrthoBill::getCreatedDateTime).reversed());
                    }
            }
        }
        return orthoBills;
    }
    private List<OrthoBill> buildBillPaginationPage(List<OrthoBill> newOrthoBills, PaginationData paginationData){
        List<OrthoBill> orthoBills = new ArrayList<>();
        int indexLoop = 0;
        int countReturn = 0;
        int pageNo = paginationData.getPageNo() > 0 ? paginationData.getPageNo()*paginationData.getPageSize() : paginationData.getPageNo();
        for (OrthoBill orthoBill: newOrthoBills){
            if(paginationData.getFindItem().equalsIgnoreCase(ASTERISK)){
                List<Integer> increment = billIncrementLoop(countReturn, indexLoop, pageNo, paginationData.getPageSize(), orthoBills, orthoBill);
                countReturn = increment.get(0);
                indexLoop = increment.get(1);
            } else {
                OrthoBillDataResponse billConverted = new OrthoBillDataResponse();
                BeanUtils.copyProperties(orthoBill, billConverted);
                switch (paginationData.getSortBy()){
                    case BILL_NAME:
                        if(billConverted.equalsBillName(paginationData.getFindItem())){
                            List<Integer> increment = billIncrementLoop(countReturn, indexLoop, pageNo, paginationData.getPageSize(), orthoBills, orthoBill);
                            countReturn = increment.get(0);
                            indexLoop = increment.get(1);
                        }
                        break;
                    case TOTAL_BILL:
                        if(billConverted.equalsTotalBill(paginationData.getFindItem())){
                            List<Integer> increment = billIncrementLoop(countReturn, indexLoop, pageNo, paginationData.getPageSize(), orthoBills, orthoBill);
                            countReturn = increment.get(0);
                            indexLoop = increment.get(1);
                        }
                        break;
                    default:
                        if(billConverted.equalsBillName(paginationData.getFindItem())){
                            List<Integer> increment = billIncrementLoop(countReturn, indexLoop, pageNo, paginationData.getPageSize(), orthoBills, orthoBill);
                            countReturn = increment.get(0);
                            indexLoop = increment.get(1);
                            break;
                        } else if(billConverted.equalsTotalBill(paginationData.getFindItem())){
                            List<Integer> increment = billIncrementLoop(countReturn, indexLoop, pageNo, paginationData.getPageSize(), orthoBills, orthoBill);
                            countReturn = increment.get(0);
                            indexLoop = increment.get(1);
                            break;
                        }
                }
            }
        }
        return orthoBills;
    }
    private List<Integer> billIncrementLoop(int countReturn, int indexLoop, int pageNo, Integer pageSize, List<OrthoBill> orthoBills, OrthoBill orthoBill) {
        if(indexLoop >= pageNo && countReturn <= pageSize){
            orthoBills.add(orthoBill);
            countReturn++;
        }
        indexLoop++;
        return Arrays.asList(countReturn, indexLoop);
    }
    // Computation for Total Bill, Total Balance, Total Payment, Total Additional Charge
    protected BillsTotalName buildComputeTotalBillBalancePaymentAdditionalCharge(OrthoBill orthoBill) {
        //TODO
        // 1.  compute breakdown payment transaction
        // 1.1 check payment history for latest update
        // 2.  compute additional charge transaction
        // 2.2 check additional charge history for latest update
        // 3.  add addition charge to total bill
        // 4.  computation = (total bill + total additional charge) - payment
        String billName = orthoBill.getBillName();
        double totalBill = orthoBill.getTotalBill();
        double totalPayment = 0.0;
        double totalAdditionalCharge = 0.0;
        BillLatest billLatest = getBillLatest(orthoBill, totalBill, billName);
        List<PayOrChargeTransaction> transactions = payOrChargeTransactionRepository.findByOrthoBillId(orthoBill.getId());
        if (!transactions.isEmpty()) {
            for (PayOrChargeTransaction transaction : transactions) {
                if (transaction.getTransactionType().equalsIgnoreCase(PAYMENT)){
                    List<PaymentHistory> paymentHistory = paymentHistoryRepository.findByTransactionId(transaction.getId());
                    if (!paymentHistory.isEmpty()) {
                        paymentHistory.sort(Comparator.comparing(PaymentHistory::getCreatedDateTime).reversed());
                        PaymentHistory paymentLatest = paymentHistory.get(0);
                        totalPayment += paymentLatest.getPaymentAmount();
                    } else {
                        totalPayment += transaction.getPaymentAmount();
                    }
                } else if (transaction.getTransactionType().equalsIgnoreCase(ADDITIONAL_CHARGE)){
                    totalAdditionalCharge = getTotalAdditionalCharge(transaction, totalAdditionalCharge);
                }
            }
        }
        return BillsTotalName.builder()
                .billName(billLatest.getBillName())
                .totalPayment(totalPayment)
                .totalBalance(billLatest.getTotalBill() - totalPayment)
                .totalAdditionalCharge(totalAdditionalCharge)
                .totalBill(billLatest.getTotalBill())
                .build();
    }

    private double getTotalAdditionalCharge(PayOrChargeTransaction transaction, double totalAdditionalCharge) {
        List<AdditionalChargeHistory> additionalChargeHistory = additionalChargeHistoryRepository.findByTransactionId(transaction.getId());
        if(!additionalChargeHistory.isEmpty()){
            additionalChargeHistory.sort(Comparator.comparing(AdditionalChargeHistory::getCreatedDateTime).reversed());
            AdditionalChargeHistory additionalChargeLatest = additionalChargeHistory.get(0);
            totalAdditionalCharge += additionalChargeLatest.getAdditionalChargeAmount();
        } else {
            totalAdditionalCharge += transaction.getAdditionalChargeAmount();
        }
        return totalAdditionalCharge;
    }

    // Compute Bill Latest Update
    protected BillLatest getBillLatest(OrthoBill orthoBill, double totalBill, String billName) {
        List<BillChangedHistory> billChangedHistoryList = billChangedHistoryRepository.findByOrthoBillId(orthoBill.getId());
        if (!billChangedHistoryList.isEmpty()) {
            billChangedHistoryList.sort(Comparator.comparing(BillChangedHistory::getCreatedDateTime).reversed());
            BillChangedHistory billLatest = billChangedHistoryList.get(0);
            totalBill = billLatest.getTotalBill();
            billName = billLatest.getBillName();
        }
        return BillLatest.builder()
                .billName(billName)
                .totalBill(totalBill)
                .build();
    }
}