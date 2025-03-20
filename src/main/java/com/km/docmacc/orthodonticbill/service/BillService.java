package com.km.docmacc.orthodonticbill.service;

import com.km.docmacc.orthodonticbill.model.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface BillService {
    ResponseEntity<Set<Long>> getPatientsWithBill();
    ResponseEntity<List<OrthoBillDataResponse>> getOrthoBillList(PaginationData paginationData);
    ResponseEntity<OrthoBillDataResponse> getOrthoBill(Long billId, Long profileId);
    ResponseEntity<List<BillBreakdown>> getBillBreakdownList(PaginationData paginationData);
    ResponseEntity<List<OrthoBillDataChangeResponse>> getBillChangeList(PaginationData paginationData);
    ResponseEntity<List<AdditionalChargeResponse>> getAdditionalChargeList(PaginationData paginationData);
    ResponseEntity<AdditionalChargeResponse> getAdditionalCharge(Long chargeId);
    ResponseEntity<List<PaymentResponse>> getPaymentList(PaginationData paginationData);
    ResponseEntity<PaymentResponse> getPayment(Long paymentId);
    ResponseEntity<HttpResponse> insertBill(OrthoBillDataRequest dataRequest);
    ResponseEntity<HttpResponse> insertUpdateBill(OrthoBillDataChangeRequest dataRequest);
    ResponseEntity<HttpResponse> insertAdditionalCharge(AdditionalChargeRequest dataRequest);
    ResponseEntity<HttpResponse> insertUpdateAdditionalCharge(AdditionalChargeUpdateRequest dataRequest);
    ResponseEntity<HttpResponse> insertPayment(PaymentRequest dataRequest);
    ResponseEntity<HttpResponse> insertUpdatePayment(PaymentUpdateRequest dataRequest);
}
