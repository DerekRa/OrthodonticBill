package com.km.docmacc.orthodonticbill.resource;

import com.km.docmacc.orthodonticbill.model.dto.*;
import com.km.docmacc.orthodonticbill.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.*;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("api/v1/orthodonticBill")
public class BillController extends BuildValidationResponseEntity {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }
    // Added on service for angular - Feb 9, 2025
    @PostMapping
    public ResponseEntity<HttpResponse> saveBill(@RequestBody OrthoBillDataRequest dataRequest) {
        log.info("Orthodontic bill data ::{}", dataRequest);
        ResponseEntity<HttpResponse> validation = validationOrthodonticBillDataRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.insertBill(dataRequest) : validation;
    }
    // Added on service for angular - Feb 10, 2025
    @PostMapping(FORWARD_SLASH + UPDATE)
    public ResponseEntity<HttpResponse> updateBill(@RequestBody OrthoBillDataChangeRequest dataRequest) {
        log.info("Orthodontic bill update data ::{}", dataRequest);
        ResponseEntity<HttpResponse> validation = validationOrthoBillDataChangeRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.insertUpdateBill(dataRequest) : validation;
    }
    // Added on service for angular - Feb 11, 2025
    @PostMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + ADDITIONAL_CHARGE)
    public ResponseEntity<HttpResponse> saveAdditionalCharge(@RequestBody AdditionalChargeRequest dataRequest) {
        log.info("Orthodontic bill additional charge data ::{}", dataRequest);
        ResponseEntity<HttpResponse> validation = validationAdditionalChargeRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.insertAdditionalCharge(dataRequest) : validation;
    }
    // Added on service for angular - Feb 12, 2025
    @PostMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + ADDITIONAL_CHARGE + FORWARD_SLASH + UPDATE)
    public ResponseEntity<HttpResponse> saveAdditionalChargeUpdate(@RequestBody AdditionalChargeUpdateRequest dataRequest) {
        log.info("Orthodontic bill additional charge update data ::{}", dataRequest);
        ResponseEntity<HttpResponse> validation = validationAdditionalChargeUpdateRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.insertUpdateAdditionalCharge(dataRequest) : validation;
    }
    // Added on service for angular - Feb 11, 2025
    @PostMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + PAYMENT)
    public ResponseEntity<HttpResponse> savePayment(@RequestBody PaymentRequest dataRequest) {
        log.info("Orthodontic bill payment data ::{}", dataRequest);
        ResponseEntity<HttpResponse> validation = validationPaymentRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.insertPayment(dataRequest) : validation;
    }
    // Added on service for angular - Feb 11, 2025
    @PostMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + PAYMENT + FORWARD_SLASH + UPDATE)
    public ResponseEntity<HttpResponse> savePaymentUpdate(@RequestBody PaymentUpdateRequest dataRequest) {
        log.info("Orthodontic bill payment update data ::{}", dataRequest);
        ResponseEntity<HttpResponse> validation = validationPaymentUpdateRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.insertUpdatePayment(dataRequest) : validation;
    }
    // Added on service for angular - Feb 9, 2025
    @GetMapping(FORWARD_SLASH + PATIENTS_WITH_RECORDS)// done - working on angular
    public ResponseEntity<Set<Long>> getAllActivePatients(){
        return billService.getPatientsWithBill();
    }
    // Added on service for angular - Feb 9, 2025
    @GetMapping
    public ResponseEntity<List<OrthoBillDataResponse>> getPaginatedBill(@RequestParam Long profileId, @RequestParam Integer pageNo,
                                                                                @RequestParam Integer pageSize, @RequestParam String sortBy,
                                                                                @RequestParam String orderBy, @RequestParam String findItem) {
        log.info("Get orthodontic bills with request data profileId : {}, pageNo : {}, pageSize : {}, sortBy : {}, orderBy : {}, findItem : {}",
                profileId, pageNo, pageSize, sortBy, orderBy, findItem);
        PaginationData dataRequest = PaginationData.builder()
                .id(profileId)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .orderBy(orderBy)
                .findItem(findItem)
                .build();
        ResponseEntity<List<OrthoBillDataResponse>> validation = validationPaginationRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.getOrthoBillList(dataRequest) : validation;
    }
    // Added on service for angular - Feb 10, 2025
    @GetMapping(FORWARD_SLASH + OPEN_CURLY_BRACKET + PROFILE_ID + CLOSE_CURLY_BRACKET + FORWARD_SLASH + OPEN_CURLY_BRACKET + BILL_ID + CLOSE_CURLY_BRACKET)
    public ResponseEntity<OrthoBillDataResponse> getBill(@PathVariable(PROFILE_ID) Long profileId, @PathVariable(BILL_ID) Long billId){
        log.info("Get orthodontic bill with request data profileId : {}, billId : {}", profileId, billId);
        ResponseEntity<OrthoBillDataResponse> validation = validateProfileIdBillId(profileId,billId);
        return  validation.getStatusCode().equals(OK) ?
                billService.getOrthoBill(billId, profileId) : validation;
    }
    // Added on service for angular - Feb 9, 2025
    @GetMapping(FORWARD_SLASH + BREAKDOWN)
    public ResponseEntity<List<BillBreakdown>> getPaginatedBillBreakdown(@RequestParam Long billId, @RequestParam Integer pageNo,
                                                                        @RequestParam Integer pageSize, @RequestParam String sortBy,
                                                                        @RequestParam String orderBy, @RequestParam String findItem) {
        log.info("Get orthodontic bill breakdown with request data billId : {}, pageNo : {}, pageSize : {}, sortBy : {}, orderBy : {}, findItem : {}",
                billId, pageNo, pageSize, sortBy, orderBy, findItem);
        PaginationData dataRequest = PaginationData.builder()
                .id(billId)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .orderBy(orderBy)
                .findItem(findItem)
                .build();
        ResponseEntity<List<BillBreakdown>> validation = validationPaginationBreakdownRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.getBillBreakdownList(dataRequest) : validation;
    }
    // Added on service for angular - Feb 10, 2025
    @GetMapping(FORWARD_SLASH + HISTORY)
    public ResponseEntity<List<OrthoBillDataChangeResponse>> getPaginatedBillHistory(@RequestParam Long billId, @RequestParam Integer pageNo,
                                                                         @RequestParam Integer pageSize, @RequestParam String sortBy,
                                                                         @RequestParam String orderBy, @RequestParam String findItem) {
        log.info("Get orthodontic bill changes history with request data billId : {}, pageNo : {}, pageSize : {}, sortBy : {}, orderBy : {}, findItem : {}",
                billId, pageNo, pageSize, sortBy, orderBy, findItem);
        PaginationData dataRequest = PaginationData.builder()
                .id(billId)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .orderBy(orderBy)
                .findItem(findItem)
                .build();
        ResponseEntity<List<OrthoBillDataChangeResponse>> validation = validationPaginationBillHistoryRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.getBillChangeList(dataRequest) : validation;
    }
    // Added on service for angular - Feb 12, 2025
    @GetMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + ADDITIONAL_CHARGE + FORWARD_SLASH + HISTORY)
    public ResponseEntity<List<AdditionalChargeResponse>> getPaginatedAdditionalChargeHistory(@RequestParam Long transactionId, @RequestParam Integer pageNo,
                                                                         @RequestParam Integer pageSize, @RequestParam String sortBy,
                                                                         @RequestParam String orderBy, @RequestParam String findItem) {
        log.info("Get orthodontic bill additional charge history with request data transactionId : {}, pageNo : {}, pageSize : {}, sortBy : {}, orderBy : {}, findItem : {}",
                transactionId, pageNo, pageSize, sortBy, orderBy, findItem);
        PaginationData dataRequest = PaginationData.builder()
                .id(transactionId)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .orderBy(orderBy)
                .findItem(findItem)
                .build();
        ResponseEntity<List<AdditionalChargeResponse>> validation = validationPaginationAdditionalChargeHistoryRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.getAdditionalChargeList(dataRequest) : validation;
    }
    // Added on service for angular - Feb 12, 2025
    @GetMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + ADDITIONAL_CHARGE + FORWARD_SLASH + HISTORY + FORWARD_SLASH + OPEN_CURLY_BRACKET + CHARGE_ID + CLOSE_CURLY_BRACKET)
    public ResponseEntity<AdditionalChargeResponse> getAdditionalCharge(@PathVariable(CHARGE_ID) Long chargeId){
        log.info("Get orthodontic bill additional charge with request data chargeId : {}", chargeId);
        ResponseEntity<AdditionalChargeResponse> validation = validateChargeId(chargeId);
        return  validation.getStatusCode().equals(OK) ?
                billService.getAdditionalCharge(chargeId) : validation;
    }
    // Added on service for angular - Feb 11, 2025
    @GetMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + PAYMENT + FORWARD_SLASH + HISTORY)
    public ResponseEntity<List<PaymentResponse>> getPaginatedPaymentHistory(@RequestParam Long transactionId, @RequestParam Integer pageNo,
                                                                                              @RequestParam Integer pageSize, @RequestParam String sortBy,
                                                                                              @RequestParam String orderBy, @RequestParam String findItem) {
        log.info("Get orthodontic bill payment history with request data transactionId : {}, pageNo : {}, pageSize : {}, sortBy : {}, orderBy : {}, findItem : {}",
                transactionId, pageNo, pageSize, sortBy, orderBy, findItem);
        PaginationData dataRequest = PaginationData.builder()
                .id(transactionId)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .orderBy(orderBy)
                .findItem(findItem)
                .build();
        ResponseEntity<List<PaymentResponse>> validation = validationPaginationPaymentHistoryRequest(dataRequest);
        return validation.getStatusCode().equals(OK) ?
                billService.getPaymentList(dataRequest) : validation;
    }
    // Added on service for angular - Feb 11, 2025
    @GetMapping(FORWARD_SLASH + BREAKDOWN + FORWARD_SLASH + PAYMENT + FORWARD_SLASH + HISTORY + FORWARD_SLASH+ OPEN_CURLY_BRACKET + PAYMENT_ID + CLOSE_CURLY_BRACKET)
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable(PAYMENT_ID) Long paymentId){
        log.info("Get orthodontic bill payment with request data paymentId : {}", paymentId);
        ResponseEntity<PaymentResponse> validation = validatePaymentId(paymentId);
        return  validation.getStatusCode().equals(OK) ?
                billService.getPayment(paymentId) : validation;
    }
}
