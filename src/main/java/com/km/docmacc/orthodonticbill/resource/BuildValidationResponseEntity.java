package com.km.docmacc.orthodonticbill.resource;

import com.km.docmacc.orthodonticbill.model.dto.*;
import com.km.docmacc.orthodonticbill.service.impl.BuildResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.*;
import static com.km.docmacc.orthodonticbill.constants.BillGlobalConstants.CREATED_BY_NAME_ID_EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
public class BuildValidationResponseEntity extends BuildResponseEntity {
    protected ResponseEntity<HttpResponse> validationOrthodonticBillDataRequest(OrthoBillDataRequest dataRequest) {
        String orthodonticBillMessage = "Orthodontic Bill (" + dataRequest.getBillName() + ")";
        if (dataRequest.getProfileId() <= 0L) {
            log.warn("Orthodontic Bill - Profile ID to save is different.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + PROFILE_ID_FAILED);
        }
        if (dataRequest.getBillName().isEmpty()) {
            log.warn("Orthodontic Bill - Bill Name being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + BILL_NAME_EMPTY);
        }
        if (dataRequest.getTotalBill() <= 0) {
            log.warn("Orthodontic Bill - Total Bill being save is 0.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + TOTAL_BILL_ZERO);
        }
        if (dataRequest.getCreatedByName().isEmpty() || dataRequest.getCreatedById().isEmpty()) {
            log.warn("Orthodontic Bill - Created By Name or ID being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + CREATED_BY_NAME_ID_EMPTY);
        }
        return httpResponse(OK, "ok");
    }

    protected ResponseEntity<HttpResponse> validationOrthoBillDataChangeRequest(OrthoBillDataChangeRequest dataRequest) {
        String orthodonticBillMessage = "Orthodontic Bill Update (" + dataRequest.getBillName() + ")";
        if (dataRequest.getBillId() <= 0L) {
            log.warn("Orthodontic Bill - Bill ID to save is different.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + BILL_ID_FAILED);
        }
        if (dataRequest.getBillName().isEmpty()) {
            log.warn("Orthodontic Bill - Bill Name field being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + BILL_NAME_EMPTY);
        }
        if (dataRequest.getTotalBill() <= 0) {
            log.warn("Orthodontic Bill - Total Bill field being save is 0.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + TOTAL_BILL_ZERO);
        }
        if (dataRequest.getReasonChanged().isEmpty()) {
            log.warn("Orthodontic Bill - Reason of changed field being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + REASON_CHANGE_EMPTY);
        }
        if (dataRequest.getCreatedByName().isEmpty() || dataRequest.getCreatedById().isEmpty()) {
            log.warn("Orthodontic Bill - Created By Name or ID being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + CREATED_BY_NAME_ID_EMPTY);
        }
        return httpResponse(OK, "ok");
    }

    protected ResponseEntity<HttpResponse> validationAdditionalChargeRequest(AdditionalChargeRequest dataRequest) {
        String orthodonticBillMessage = "Orthodontic Bill Add Additional Charge (" + dataRequest.getAdditionalChargeAmount() + ")";
        if (dataRequest.getBillId() <= 0L) {
            log.warn("Orthodontic Bill - Bill ID to save is different.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + BILL_ID_FAILED);
        }
        if (dataRequest.getAdditionalChargeAmount() <= 0) {
            log.warn("Orthodontic Bill - Additional Charge field being save is less than equal 0.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + ADDITIONAL_CHARGE_ZERO);
        }
        if (dataRequest.getCreatedByName().isEmpty() || dataRequest.getCreatedById().isEmpty()) {
            log.warn("Orthodontic Bill - Created By Name or ID being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + CREATED_BY_NAME_ID_EMPTY);
        }
        return httpResponse(OK, "ok");
    }

    protected ResponseEntity<HttpResponse> validationAdditionalChargeUpdateRequest(AdditionalChargeUpdateRequest dataRequest) {
        String orthodonticBillMessage = "Orthodontic Bill Add Additional Charge Update (" + dataRequest.getAdditionalChargeAmount() + ")";
        if (dataRequest.getChargeTransactionId() <= 0L) {
            log.warn("Orthodontic Bill - Transaction ID to save is different.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + TRANSACTION_ID_FAILED);
        }
        if (dataRequest.getAdditionalChargeAmount() <= 0) {
            log.warn("Orthodontic Bill - Additional Charge field being save is less than equal 0.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + ADDITIONAL_CHARGE_ZERO);
        }
        if (dataRequest.getReasonChange().isEmpty()) {
            log.warn("Orthodontic Bill - Reason of change field being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + REASON_CHANGE_EMPTY);
        }
        if (dataRequest.getCreatedByName().isEmpty() || dataRequest.getCreatedById().isEmpty()) {
            log.warn("Orthodontic Bill - Created By Name or ID being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + CREATED_BY_NAME_ID_EMPTY);
        }
        return httpResponse(OK, "ok");
    }

    protected ResponseEntity<HttpResponse> validationPaymentRequest(PaymentRequest dataRequest) {
        String orthodonticBillMessage = "Orthodontic Bill Add Payment (" + dataRequest.getPayment() + ")";
        if (dataRequest.getBillId() <= 0L) {
            log.warn("Orthodontic Bill - Bill ID to save is different.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + BILL_ID_FAILED);
        }
        if (dataRequest.getPayment() <= 0) {
            log.warn("Orthodontic Bill - Payment field being save is less than equal 0.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + PAYMENT_ZERO);
        }
        if (dataRequest.getNote().isEmpty()) {
            log.warn("Orthodontic Bill - Note field being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + NOTE_EMPTY);
        }
        if (dataRequest.getCreatedByName().isEmpty() || dataRequest.getCreatedById().isEmpty()) {
            log.warn("Orthodontic Bill - Created By Name or ID being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + CREATED_BY_NAME_ID_EMPTY);
        }
        return httpResponse(OK, "ok");
    }

    protected ResponseEntity<HttpResponse> validationPaymentUpdateRequest(PaymentUpdateRequest dataRequest) {
        String orthodonticBillMessage = "Orthodontic Bill Add Payment Update (" + dataRequest.getPayment() + ")";
        if (dataRequest.getPaymentTransactionId() <= 0L) {
            log.warn("Orthodontic Bill - Transaction ID to save is different.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + TRANSACTION_ID_FAILED);
        }
        if (dataRequest.getPayment() <= 0) {
            log.warn("Orthodontic Bill - Payment field being save is less than equal 0.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + PAYMENT_ZERO);
        }
        if (dataRequest.getNote().isEmpty()) {
            log.warn("Orthodontic Bill - Note field being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + NOTE_EMPTY);
        }
        if (dataRequest.getReasonChange().isEmpty()) {
            log.warn("Orthodontic Bill - Reason of change field being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + REASON_CHANGE_EMPTY);
        }
        if (dataRequest.getCreatedByName().isEmpty() || dataRequest.getCreatedById().isEmpty()) {
            log.warn("Orthodontic Bill - Created By Name or ID being save is empty.");
            return httpResponse(BAD_REQUEST, orthodonticBillMessage + DASH + SAVE + CREATED_BY_NAME_ID_EMPTY);
        }
        return httpResponse(OK, "ok");
    }
    protected ResponseEntity<List<OrthoBillDataResponse>> validationPaginationRequest(PaginationData requestDto) {
        List<OrthoBillDataResponse> orthodonticBillDataResponses = new ArrayList<>();
        if (requestDto.getId() <= 0L) {
            log.warn("The Orthodontic Bill ID to get is different.");
            return orthoBillDataResponseList(BAD_REQUEST, orthodonticBillDataResponses);
        }
        return orthoBillDataResponseList(OK, orthodonticBillDataResponses);
    }
    protected ResponseEntity<List<BillBreakdown>> validationPaginationBreakdownRequest(PaginationData requestDto) {
        List<BillBreakdown> billBreakdowns = new ArrayList<>();
        if (requestDto.getId() <= 0L) {
            log.warn("The Orthodontic Bill ID to get is different.");
            return billBreakdownList(BAD_REQUEST, billBreakdowns);
        }
        return billBreakdownList(OK, billBreakdowns);
    }
    protected ResponseEntity<List<OrthoBillDataChangeResponse>> validationPaginationBillHistoryRequest(PaginationData requestDto) {
        List<OrthoBillDataChangeResponse> orthoBillDataChangeResponses = new ArrayList<>();
        if (requestDto.getId() <= 0L) {
            log.warn("The Orthodontic Transaction ID to get is different.");
            return billHistoryList(BAD_REQUEST, orthoBillDataChangeResponses);
        }
        return billHistoryList(OK, orthoBillDataChangeResponses);
    }
    protected ResponseEntity<List<AdditionalChargeResponse>> validationPaginationAdditionalChargeHistoryRequest(PaginationData requestDto) {
        List<AdditionalChargeResponse> additionalChargeResponses = new ArrayList<>();
        if (requestDto.getId() <= 0L) {
            log.warn("The Orthodontic Additional Charge Transaction ID to get is different.");
            return additionalChargeHistoryList(BAD_REQUEST, additionalChargeResponses);
        }
        return additionalChargeHistoryList(OK, additionalChargeResponses);
    }
    protected ResponseEntity<List<PaymentResponse>> validationPaginationPaymentHistoryRequest(PaginationData requestDto) {
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        if (requestDto.getId() <= 0L) {
            log.warn("The Orthodontic Payment Transaction ID to get is different.");
            return paymentHistoryList(BAD_REQUEST, paymentResponses);
        }
        return paymentHistoryList(OK, paymentResponses);
    }
    protected ResponseEntity<OrthoBillDataResponse> validateProfileIdBillId(Long profileId, Long billId) {
        OrthoBillDataResponse orthoBillDataResponse = new OrthoBillDataResponse();
        if (profileId <= 0L) {
            log.warn("The Orthodontic Profile ID to get is different.");
            return orthoBillDataResponse(BAD_REQUEST, orthoBillDataResponse);
        }
        if (billId <= 0L) {
            log.warn("The Orthodontic Bill ID to get is different.");
            return orthoBillDataResponse(BAD_REQUEST, orthoBillDataResponse);
        }
        return orthoBillDataResponse(OK, orthoBillDataResponse);
    }
    protected ResponseEntity<AdditionalChargeResponse> validateChargeId(Long chargeId) {
        AdditionalChargeResponse additionalChargeResponse = new AdditionalChargeResponse();
        if (chargeId <= 0L) {
            log.warn("The Orthodontic Charge ID to get is different.");
            return additionalChargeResponse(BAD_REQUEST, additionalChargeResponse);
        }
        return additionalChargeResponse(OK, additionalChargeResponse);
    }
    protected ResponseEntity<PaymentResponse> validatePaymentId(Long paymentId) {
        PaymentResponse additionalChargeResponse = new PaymentResponse();
        if (paymentId <= 0L) {
            log.warn("The Orthodontic Payment ID to get is different.");
            return paymentResponse(BAD_REQUEST, additionalChargeResponse);
        }
        return paymentResponse(OK, additionalChargeResponse);
    }
}
