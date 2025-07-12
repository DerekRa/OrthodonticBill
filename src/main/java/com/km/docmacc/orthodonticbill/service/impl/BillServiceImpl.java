package com.km.docmacc.orthodonticbill.service.impl;

import com.km.docmacc.orthodonticbill.model.dto.*;
import com.km.docmacc.orthodonticbill.model.entity.*;
import com.km.docmacc.orthodonticbill.repository.*;
import com.km.docmacc.orthodonticbill.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.*;
import static org.springframework.data.jpa.domain.AbstractAuditable_.CREATED_DATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
public class BillServiceImpl extends BillServiceImplBuilder implements BillService {
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

    /**
     * @return
     */
    @Override
    public ResponseEntity<Set<Long>> getPatientsWithBill() {
        List<OrthoBill> orthoBills = orthoBillRepository.findAll();
        Set<Long> profileIds = new HashSet<>();
        for (OrthoBill orthoBill: orthoBills){
            profileIds.add(orthoBill.getProfileId());
        }
        return patientsWithBill(OK, profileIds);
    }

    /**
     * @param paginationData
     * @return List of OrthoBillDataResponse
     */
    @Override
    public ResponseEntity<List<OrthoBillDataResponse>> getOrthoBillList(PaginationData paginationData) {
        Sort sort = paginationData.getSortBy().equalsIgnoreCase(SEARCH_ALL_COLUMNS) ? Sort.by(CREATEDDATE).ascending() :
                paginationData.getOrderBy().equals(ASC) ? Sort.by(paginationData.getSortBy()).ascending() :
                        paginationData.getSortBy().equalsIgnoreCase(TOTAL_BALANCE) ? Sort.by(CREATEDDATE).descending() : Sort.by(paginationData.getSortBy()).descending();
        Pageable paging = PageRequest.of(paginationData.getPageNo(), paginationData.getPageSize(), sort);
        List<OrthoBill> orthoBills;
        if(StringUtils.equals(paginationData.getFindItem(), ASTERISK)){
            orthoBills = orthoBillRepository.findByProfileId(paginationData.getId(), paging).toList();
        } else {
            if(paginationData.getSortBy().equalsIgnoreCase(BILL_NAME) ||
                    paginationData.getSortBy().equalsIgnoreCase(TOTAL_BILL)){
                //TODO - done
                // 1. Get first original bill
                // 2. loop original bill and check if has changes
                // 3. if found a change on history, update the bill name
                // 4. search now on the new list
                orthoBills = buildBillCustomPagination(paginationData);
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_DATE)){
                DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DATE_FORMAT);
                LocalDate findCreatedDate = LocalDate.parse(paginationData.getFindItem(), formatters);
                orthoBills = orthoBillRepository.findAllByProfileIdAndCreatedDate(paginationData.getId(),
                        findCreatedDate,  paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(TOTAL_BALANCE)){
                orthoBills = orthoBillRepository.findByProfileId(paginationData.getId(), paging).toList();
            } else {
                orthoBills = buildBillCustomPagination(paginationData);
            }
        }
        log.info("Data of orthoBills ::{}",orthoBills);
        List<OrthoBillDataResponse> orthoBillDataResponses = new ArrayList<OrthoBillDataResponse>();
        if(!orthoBills.isEmpty()){
            for (OrthoBill orthoBill : orthoBills){
                OrthoBillDataResponse orthoBillDataResponse = new OrthoBillDataResponse();
                BeanUtils.copyProperties(orthoBill, orthoBillDataResponse);
                BillsTotalName billsTotalName = buildComputeTotalBillBalancePaymentAdditionalCharge(orthoBill);
                orthoBillDataResponse.setBillName(billsTotalName.getBillName());
                orthoBillDataResponse.setTotalBill(billsTotalName.getTotalBill() + billsTotalName.getTotalAdditionalCharge());
                //TODO - done
                // 1.  compute breakdown payment transaction
                // 1.1 check payment history for latest update
                // 2.  compute additional charge transaction
                // 2.2 check additional charge history for latest update
                // 3.  add addition charge to total bill
                // 4.  computation = (total bill + total additional charge) - payment
                // buildComputeTotalBalance()
                orthoBillDataResponse.setTotalBalance(billsTotalName.getTotalBalance() + billsTotalName.getTotalAdditionalCharge()); // to fix later on
                //TODO - done
                // 1. compute breakdown payment transaction
                // 2. check payment history for latest update
                orthoBillDataResponse.setTotalAmountPaid(billsTotalName.getTotalPayment()); // to fix later on
                orthoBillDataResponses.add(orthoBillDataResponse);
            }
            //TODO - done
            // 1. Pagination for totalBalance
            if(paginationData.getSortBy().equalsIgnoreCase(TOTAL_BALANCE)){
                orthoBillDataResponses = buildTotalBalanceCustomPagination(orthoBillDataResponses, paginationData);
            }
            return orthoBillDataResponseList(OK, orthoBillDataResponses);
        }
        return orthoBillDataResponseList(BAD_REQUEST, orthoBillDataResponses);
    }
    /**
     * @param billId
     * @param profileId
     * @return OrthoBillDataResponse
     */
    @Override
    public ResponseEntity<OrthoBillDataResponse> getOrthoBill(Long billId, Long profileId) {
        Optional<OrthoBill> orthoBill = orthoBillRepository.findByIdAndProfileId(billId, profileId);
        OrthoBillDataResponse orthoBillDataResponse = new OrthoBillDataResponse();
        if(orthoBill.isPresent()){
            BeanUtils.copyProperties(orthoBill.get(), orthoBillDataResponse);
            BillsTotalName totalName = buildComputeTotalBillBalancePaymentAdditionalCharge(orthoBill.get());
            orthoBillDataResponse.setTotalBalance(totalName.getTotalBalance()); // to fix later on IF IT WILL BE INCLUDED ON DISPLAY
            //TODO - done
            // 1. compute breakdown payment transaction
            // 2. check payment history for latest update
            orthoBillDataResponse.setBillName(totalName.getBillName());
            orthoBillDataResponse.setTotalBill(totalName.getTotalBill());
            orthoBillDataResponse.setTotalAmountPaid(totalName.getTotalPayment()); // to fix later on
            return orthoBillDataResponse(OK, orthoBillDataResponse);
        } else {
            return orthoBillDataResponse(BAD_REQUEST, orthoBillDataResponse);
        }
    }
    /**
     * @param paginationData
     * @return List of BillBreakdown
     */
    @Override
    public ResponseEntity<List<BillBreakdown>> getBillBreakdownList(PaginationData paginationData) {
        Sort sort = paginationData.getSortBy().equalsIgnoreCase(SEARCH_ALL_COLUMNS) ? Sort.by(CREATEDDATE).ascending() :
                paginationData.getOrderBy().equals(ASC) ? Sort.by(paginationData.getSortBy()).ascending() :
                        Sort.by(paginationData.getSortBy()).descending();
        Pageable paging = PageRequest.of(paginationData.getPageNo(), paginationData.getPageSize(), sort);
        List<PayOrChargeTransaction> transactions;
        if(StringUtils.equals(paginationData.getFindItem(), ASTERISK)){
            transactions = payOrChargeTransactionRepository.findByOrthoBillId(paginationData.getId(), paging).toList();
        } else {
            try {
                Double.parseDouble(paginationData.getFindItem());
                transactions = payOrChargeTransactionRepository.findByTransaction(paginationData.getId(), paginationData.getFindItem(), paging).toList();
            } catch (NumberFormatException e){
                transactions = payOrChargeTransactionRepository.findByOrthoBillIdAndPaymentNoteLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            }
        }
        log.info("Data of transactions ::{}",transactions);
        List<BillBreakdown> billBreakdowns = new ArrayList<BillBreakdown>();
        if(!transactions.isEmpty()){
            double totalBill = 0.0;
            Optional<OrthoBill> orthoBill = orthoBillRepository.findById(paginationData.getId());
            if(orthoBill.isPresent()){
                BillLatest billLatest = getBillLatest(orthoBill.get(), orthoBill.get().getTotalBill(), orthoBill.get().getBillName());
                totalBill = billLatest.getTotalBill();
            }
            double totalPayment = 0.0;
            //TODO - done
            // 1. Get first Bill Info
            // 2. Check bill history for latest update
            // 3. Used the details on the loop below
            // 4. Declare totalBill on a parameter ex totalBill = 40000.0
            // 5. Declare payment parameter here ex payment = 0.0
            for (PayOrChargeTransaction transaction : transactions){
                BillBreakdown breakdown = new BillBreakdown();
                BeanUtils.copyProperties(transaction, breakdown);
                //TODO - done
                // 1. check transaction type if payment or additional charge
                // 2. if payment, check latest update on history
                // 3. update payment amount and note
                // 4. if additional charge, check latest update on history
                // 5. update additional charge

                //TODO - done
                // 1. Add total bill parameter here from above
                breakdown.setTotalBill(totalBill); // to fix later on
                //TODO - done
                // 1. Increment transaction payment to payment parameter above
                // 2. use latest payment from history
                if (transaction.getTransactionType().equalsIgnoreCase(PAYMENT)){
                    List<PaymentHistory> paymentHistory = paymentHistoryRepository.findByTransactionId(transaction.getId());
                    if (!paymentHistory.isEmpty()) {
                        paymentHistory.sort(Comparator.comparing(PaymentHistory::getCreatedDateTime).reversed());
                        PaymentHistory paymentLatest = paymentHistory.get(0);
                        totalPayment += paymentLatest.getPaymentAmount();
                        totalBill -= paymentLatest.getPaymentAmount();
                        breakdown.setPaymentAmount(paymentLatest.getPaymentAmount());
                    } else {
                        totalPayment += transaction.getPaymentAmount();
                        totalBill -= transaction.getPaymentAmount();
                    }
                    breakdown.setTotalAmountPaid(totalPayment);
                } else {
                    breakdown.setPaymentAmount(0.0);
                    breakdown.setTotalAmountPaid(totalPayment); // to fix later on
                }
                if (transaction.getTransactionType().equalsIgnoreCase(ADDITIONAL_CHARGE)){
                    List<AdditionalChargeHistory> additionalChargeHistory = additionalChargeHistoryRepository.findByTransactionId(transaction.getId());
                    if(!additionalChargeHistory.isEmpty()){
                        additionalChargeHistory.sort(Comparator.comparing(AdditionalChargeHistory::getCreatedDateTime).reversed());
                        AdditionalChargeHistory additionalChargeLatest = additionalChargeHistory.get(0);
                        totalBill += additionalChargeLatest.getAdditionalChargeAmount();
                        breakdown.setAdditionalChargeAmount(additionalChargeLatest.getAdditionalChargeAmount());
                    } else {
                        totalBill += transaction.getAdditionalChargeAmount();
                    }
                } else {
                    breakdown.setAdditionalChargeAmount(0.0);
                }
                //TODO - done
                // 1. After setting total bill above
                // 2. Deduct Payment from total bill
                // 3. check first latest payment update
                // 4. computation => totalBill = totalBill - payment (latest)
                // 5. if transaction type is additional charge
                // 6. check first latest update on additional charge history
                // 7. computation => totalBill = totalBill + additional charge (latest)
                breakdown.setBalance(totalBill); // to fix later on
                billBreakdowns.add(breakdown);
            }
            //reverse here
            billBreakdowns.sort(Comparator.comparing(BillBreakdown::getCreatedDateTime).reversed());
            return billBreakdownList(OK, billBreakdowns);
        }
        return billBreakdownList(BAD_REQUEST, billBreakdowns);
    }
    /**
     * @param paginationData
     * @return List of OrthoBillDataChangeResponse
     */
    @Override
    public ResponseEntity<List<OrthoBillDataChangeResponse>> getBillChangeList(PaginationData paginationData) {
        Sort sort = paginationData.getSortBy().equalsIgnoreCase(SEARCH_ALL_COLUMNS) ? Sort.by(CREATEDDATE).ascending() :
                paginationData.getOrderBy().equals(ASC) ? Sort.by(paginationData.getSortBy()).ascending() :
                        Sort.by(paginationData.getSortBy()).descending();
        Pageable paging = PageRequest.of(paginationData.getPageNo(), paginationData.getPageSize(), sort);
        List<BillChangedHistory> billChangedHistories;
        if(StringUtils.equals(paginationData.getFindItem(), ASTERISK)){
            billChangedHistories = billChangedHistoryRepository.findByOrthoBillId(paginationData.getId(), paging).toList();
        } else {
            if(paginationData.getSortBy().equalsIgnoreCase(BILL_NAME)){
                billChangedHistories = billChangedHistoryRepository.findByOrthoBillIdAndBillNameLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(TOTAL_BILL)){
                billChangedHistories = billChangedHistoryRepository.findByOrthoBillIdAndTotalBill(paginationData.getId(),
                        Double.valueOf(paginationData.getFindItem()), paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_DATE)){
                billChangedHistories = billChangedHistoryRepository.findByOrthoBillIdAndCreatedDate(paginationData.getId(),
                        LocalDate.parse(paginationData.getFindItem()),  paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(REASON_CHANGED)){
                billChangedHistories = billChangedHistoryRepository.findByOrthoBillIdAndReasonChangedLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_BY_NAME)){
                billChangedHistories = billChangedHistoryRepository.findByOrthoBillIdAndCreatedByNameLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else {
                try {
                    Double totalBill = Double.parseDouble(paginationData.getFindItem());
                    billChangedHistories = billChangedHistoryRepository.findByOrthoBillIdAndTotalBill(paginationData.getId(),
                            totalBill, paging).toList();
                } catch (NumberFormatException e){
                    billChangedHistories = billChangedHistoryRepository.findByBillChangesHistory(paginationData.getId(),
                            PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
                }
            }
        }
        log.info("Data of billChangedHistories ::{}",billChangedHistories);
        List<OrthoBillDataChangeResponse> orthoBillDataChangeResponses = new ArrayList<OrthoBillDataChangeResponse>();
        if(!billChangedHistories.isEmpty()){
            for (BillChangedHistory billChangedHistory : billChangedHistories){
                OrthoBillDataChangeResponse orthoBillDataChangeResponse = new OrthoBillDataChangeResponse();
                BeanUtils.copyProperties(billChangedHistory, orthoBillDataChangeResponse);
                orthoBillDataChangeResponses.add(orthoBillDataChangeResponse);
            }
            return billHistoryList(OK, orthoBillDataChangeResponses);
        }
        return billHistoryList(BAD_REQUEST, orthoBillDataChangeResponses);
    }
    /**
     * @param paginationData
     * @return List of AdditionalChargeResponse
     */
    @Override
    public ResponseEntity<List<AdditionalChargeResponse>> getAdditionalChargeList(PaginationData paginationData) {
        Sort sort = paginationData.getSortBy().equalsIgnoreCase(SEARCH_ALL_COLUMNS) ? Sort.by(CREATEDDATE).ascending() :
                paginationData.getOrderBy().equals(ASC) ? Sort.by(paginationData.getSortBy()).ascending() :
                        Sort.by(paginationData.getSortBy()).descending();
        Pageable paging = PageRequest.of(paginationData.getPageNo(), paginationData.getPageSize(), sort);
        List<AdditionalChargeHistory> additionalChargeHistories;
        if(StringUtils.equals(paginationData.getFindItem(), ASTERISK)){
            additionalChargeHistories = additionalChargeHistoryRepository.findByTransactionId(paginationData.getId(), paging).toList();
        } else {
            if(paginationData.getSortBy().equalsIgnoreCase(ADDITIONAL_CHARGE_AMOUNT)){
                additionalChargeHistories = additionalChargeHistoryRepository.findByTransactionIdAndAdditionalChargeAmount(paginationData.getId(),
                        Double.valueOf(paginationData.getFindItem()), paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_DATE)){
                additionalChargeHistories = additionalChargeHistoryRepository.findByTransactionIdAndCreatedDate(paginationData.getId(),
                        LocalDate.parse(paginationData.getFindItem()),  paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CHARGE_REASON_CHANGE)){
                additionalChargeHistories = additionalChargeHistoryRepository.findByTransactionIdAndChargeReasonChangeLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_BY_NAME)){
                additionalChargeHistories = additionalChargeHistoryRepository.findByTransactionIdAndCreatedByNameLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else {
                try {
                    Double totalBill = Double.parseDouble(paginationData.getFindItem());
                    additionalChargeHistories = additionalChargeHistoryRepository.findByTransactionIdAndAdditionalChargeAmount(paginationData.getId(),
                            totalBill, paging).toList();
                } catch (NumberFormatException e){
                    additionalChargeHistories = additionalChargeHistoryRepository.findByAdditionalChargeHistory(paginationData.getId(),
                            PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
                }
            }
        }
        log.info("Data of additionalChargeHistories ::{}",additionalChargeHistories);
        List<AdditionalChargeResponse> additionalChargeResponses = new ArrayList<AdditionalChargeResponse>();
        if(!additionalChargeHistories.isEmpty()){
            for (AdditionalChargeHistory additionalChargeHistory : additionalChargeHistories){
                AdditionalChargeResponse additionalChargeResponse = new AdditionalChargeResponse();
                BeanUtils.copyProperties(additionalChargeHistory, additionalChargeResponse);
                additionalChargeResponses.add(additionalChargeResponse);
            }
            return additionalChargeHistoryList(OK, additionalChargeResponses);
        }
        return additionalChargeHistoryList(BAD_REQUEST, additionalChargeResponses);
    }
    /**
     * @param chargeId
     * @return AdditionalChargeResponse
     */
    @Override
    public ResponseEntity<AdditionalChargeResponse> getAdditionalCharge(Long chargeId) {
        List<AdditionalChargeHistory> additionalChargeHistory = additionalChargeHistoryRepository.findByTransactionId(chargeId);
        AdditionalChargeResponse additionalChargeResponse = new AdditionalChargeResponse();
        if(!additionalChargeHistory.isEmpty()){
            additionalChargeHistory.sort(Comparator.comparing(AdditionalChargeHistory::getCreatedDateTime).reversed());
            //TODO - done
            // 1. Check latest update on additional charge history
            // 2. update additional charge only
            BeanUtils.copyProperties(additionalChargeHistory.get(0), additionalChargeResponse);
            return additionalChargeResponse(OK, additionalChargeResponse);
        } else {
            Optional<PayOrChargeTransaction> payOrChargeTransaction = payOrChargeTransactionRepository.findById(chargeId);
            if (payOrChargeTransaction.isPresent()){
                BeanUtils.copyProperties(payOrChargeTransaction.get(), additionalChargeResponse);
                return additionalChargeResponse(OK, additionalChargeResponse);
            }
        }
        return additionalChargeResponse(BAD_REQUEST, additionalChargeResponse);
    }
    /**
     * @param paginationData
     * @return List of PaymentResponse
     */
    @Override
    public ResponseEntity<List<PaymentResponse>> getPaymentList(PaginationData paginationData) {
        Sort sort = paginationData.getSortBy().equalsIgnoreCase(SEARCH_ALL_COLUMNS) ? Sort.by(CREATEDDATE).ascending() :
                paginationData.getOrderBy().equals(ASC) ? Sort.by(paginationData.getSortBy()).ascending() :
                        Sort.by(paginationData.getSortBy()).descending();
        Pageable paging = PageRequest.of(paginationData.getPageNo(), paginationData.getPageSize(), sort);
        List<PaymentHistory> paymentHistories;
        if(StringUtils.equals(paginationData.getFindItem(), ASTERISK)){
            paymentHistories = paymentHistoryRepository.findByTransactionId(paginationData.getId(), paging).toList();
        } else {
            if(paginationData.getSortBy().equalsIgnoreCase(PAYMENT_AMOUNT)){
                paymentHistories = paymentHistoryRepository.findByTransactionIdAndPaymentAmount(paginationData.getId(),
                        Double.valueOf(paginationData.getFindItem()), paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_DATE)){
                paymentHistories = paymentHistoryRepository.findByTransactionIdAndCreatedDate(paginationData.getId(),
                        LocalDate.parse(paginationData.getFindItem()),  paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(PAYMENT_NOTE)){
                paymentHistories = paymentHistoryRepository.findByTransactionIdAndPaymentNoteLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(PAYMENT_REASON_CHANGE)){
                paymentHistories = paymentHistoryRepository.findByTransactionIdAndPaymentReasonChangeLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else if(paginationData.getSortBy().equalsIgnoreCase(CREATED_BY_NAME)){
                paymentHistories = paymentHistoryRepository.findByTransactionIdAndCreatedByNameLike(paginationData.getId(),
                        PERCENTAGE + paginationData.getFindItem() + PERCENTAGE, paging).toList();
            } else {
                try {
                    Double paymentAmount = Double.parseDouble(paginationData.getFindItem());
                    paymentHistories = paymentHistoryRepository.findByTransactionIdAndPaymentAmount(paginationData.getId(),
                            paymentAmount, paging).toList();
                } catch (NumberFormatException e){
                    paymentHistories = paymentHistoryRepository.findByPaymentHistory(paginationData.getId(),
                            PERCENTAGE +paginationData.getFindItem()+ PERCENTAGE, paging).toList();
                }
            }
        }
        log.info("Data of paymentHistories ::{}",paymentHistories);
        List<PaymentResponse> paymentResponses = new ArrayList<PaymentResponse>();
        if(!paymentHistories.isEmpty()){
            for (PaymentHistory paymentHistory : paymentHistories){
                PaymentResponse paymentResponse = new PaymentResponse();
                BeanUtils.copyProperties(paymentHistory, paymentResponse);
                paymentResponses.add(paymentResponse);
            }
            return paymentHistoryList(OK, paymentResponses);
        }
        return paymentHistoryList(BAD_REQUEST, paymentResponses);
    }
    /**
     * @param paymentId
     * @return PaymentResponse
     */
    @Override
    public ResponseEntity<PaymentResponse> getPayment(Long paymentId) {
        List<PaymentHistory> paymentHistory = paymentHistoryRepository.findByTransactionId(paymentId);
        PaymentResponse paymentResponse = new PaymentResponse();
        if(!paymentHistory.isEmpty()){
            paymentHistory.sort(Comparator.comparing(PaymentHistory::getCreatedDateTime).reversed());
            //TODO - done
            // 1. Check latest update on payment history
            // 2. update payment and note only
            BeanUtils.copyProperties(paymentHistory.get(0), paymentResponse);
            return paymentResponse(OK, paymentResponse);
        } else {
            Optional<PayOrChargeTransaction> payOrChargeTransaction = payOrChargeTransactionRepository.findById(paymentId);
            if (payOrChargeTransaction.isPresent()){
                BeanUtils.copyProperties(payOrChargeTransaction.get(), paymentResponse);
                return paymentResponse(OK, paymentResponse);
            }
        }
        return paymentResponse(BAD_REQUEST, paymentResponse);
    }
    /**
     * @param dataRequest
     * @return HttpResponse
     */
    @Override
    public ResponseEntity<HttpResponse> insertBill(OrthoBillDataRequest dataRequest) {
        OrthoBill saved = orthoBillRepository.save(buildOrthoBill(dataRequest));
        return httpResponse(HttpStatus.CREATED, "Orthodontic Bill (" + saved.getBillName() + ")" + SAVE_SUCCESS);
    }
    /**
     * @param dataRequest
     * @return HttpResponse
     */
    @Override
    public ResponseEntity<HttpResponse> insertUpdateBill(OrthoBillDataChangeRequest dataRequest) {
        BillChangedHistory saved = billChangedHistoryRepository.save(buildBillChangedHistory(dataRequest));
        return httpResponse(HttpStatus.CREATED, "Orthodontic Bill Update (" + saved.getBillName() + ")" + SAVE_SUCCESS);
    }
    /**
     * @param dataRequest
     * @return HttpResponse
     */
    @Override
    public ResponseEntity<HttpResponse> insertAdditionalCharge(AdditionalChargeRequest dataRequest) {
        PayOrChargeTransaction saved = payOrChargeTransactionRepository.save(buildAdditionalChargeTransaction(dataRequest));
        return httpResponse(HttpStatus.CREATED, "Additional Charge (" + saved.getAdditionalChargeAmount() + ")" + SAVE_SUCCESS);
    }
    /**
     * @param dataRequest
     * @return HttpResponse
     */
    @Override
    public ResponseEntity<HttpResponse> insertUpdateAdditionalCharge(AdditionalChargeUpdateRequest dataRequest) {
        AdditionalChargeHistory saved = additionalChargeHistoryRepository.save(buildAdditionalChargeHistory(dataRequest));
        return httpResponse(HttpStatus.CREATED, "Additional Charge Update (" + saved.getAdditionalChargeAmount() + ")" + SAVE_SUCCESS);
    }
    /**
     * @param dataRequest
     * @return HttpResponse
     */
    @Override
    public ResponseEntity<HttpResponse> insertPayment(PaymentRequest dataRequest) {
        PayOrChargeTransaction saved = payOrChargeTransactionRepository.save(buildPaymentTransaction(dataRequest));
        return httpResponse(HttpStatus.CREATED, "Payment (" + saved.getPaymentAmount() + ")" + SAVE_SUCCESS);
    }
    /**
     * @param dataRequest
     * @return HttpResponse
     */
    @Override
    public ResponseEntity<HttpResponse> insertUpdatePayment(PaymentUpdateRequest dataRequest) {
        PaymentHistory saved = paymentHistoryRepository.save(buildPaymentHistoryTransaction(dataRequest));
        return httpResponse(HttpStatus.CREATED, "Payment Update (" + saved.getPaymentAmount() + ")" + SAVE_SUCCESS);
    }
}
