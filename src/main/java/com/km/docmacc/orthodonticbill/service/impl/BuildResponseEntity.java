package com.km.docmacc.orthodonticbill.service.impl;

import com.km.docmacc.orthodonticbill.model.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.*;
import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.CREATED_BY_NAME_ID_EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

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
