package com.km.docmacc.orthodonticbill.service.impl;

import com.km.docmacc.orthodonticbill.model.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class BuildResponseEntity {
    public ResponseEntity<HttpResponse> httpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
    public ResponseEntity<Set<Long>> patientsWithBill(HttpStatus httpStatus, Set<Long> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<Set<Long>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<List<OrthoBillDataResponse>> orthoBillDataResponseList(HttpStatus httpStatus, List<OrthoBillDataResponse> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<List<OrthoBillDataResponse>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<List<BillBreakdown>> billBreakdownList(HttpStatus httpStatus, List<BillBreakdown> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<List<BillBreakdown>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<List<OrthoBillBreakdown>> billBreakdownListPrint(HttpStatus httpStatus, List<OrthoBillBreakdown> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<List<OrthoBillBreakdown>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<List<OrthoBillDataChangeResponse>> billHistoryList(HttpStatus httpStatus, List<OrthoBillDataChangeResponse> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<List<OrthoBillDataChangeResponse>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<List<AdditionalChargeResponse>> additionalChargeHistoryList(HttpStatus httpStatus, List<AdditionalChargeResponse> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<List<AdditionalChargeResponse>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<List<PaymentResponse>> paymentHistoryList(HttpStatus httpStatus, List<PaymentResponse> responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<List<PaymentResponse>>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<OrthoBillDataResponse> orthoBillDataResponse(HttpStatus httpStatus, OrthoBillDataResponse responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<OrthoBillDataResponse>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<AdditionalChargeResponse> additionalChargeResponse(HttpStatus httpStatus, AdditionalChargeResponse responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<AdditionalChargeResponse>(responseDtos, headers, httpStatus);
    }
    public ResponseEntity<PaymentResponse> paymentResponse(HttpStatus httpStatus, PaymentResponse responseDtos) {
        HttpHeaders headers = new HttpHeaders();
        /*headers.setContentType(MediaType.TEXT_HTML);*/
        return new ResponseEntity<PaymentResponse>(responseDtos, headers, httpStatus);
    }
}
