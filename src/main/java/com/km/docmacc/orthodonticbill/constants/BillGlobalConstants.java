package com.km.docmacc.orthodonticbill.constants;

public class BillGlobalConstants {
    private BillGlobalConstants(){}
    public static final String SAVE_SUCCESS = " is saved successfully";
    public static final String PROFILE_ID_FAILED = " Save failed because your profile ID was different.";
    public static final String BILL_ID_FAILED = " Save failed because your bill ID was different.";
    public static final String TRANSACTION_ID_FAILED = " Save failed because your transaction ID was different.";
    public static final String BILL_NAME_EMPTY = " Save failed because bill name is empty.";
    public static final String REASON_CHANGE_EMPTY = " Save failed because reason of change is empty.";
    public static final String NOTE_EMPTY = " Save failed because note is empty.";
    public static final String TOTAL_BILL_ZERO = " Save failed because total bill is less than equal to 0.";
    public static final String ADDITIONAL_CHARGE_ZERO = " Save failed because additional charge is less than equal to 0.";
    public static final String PAYMENT_ZERO = " Save failed because payment is less than equal to 0.";
    public static final String CREATED_BY_NAME_ID_EMPTY = " Save failed PLEASE TRY TO LOGIN AGAIN.";
    /*Pagination and Sign*/
    public static final String FORWARD_SLASH = "/";
    public static final String DASH = " - ";
    public static final String ASTERISK = "**";
    public static final String PERCENTAGE = "%";
    public static final String OPEN_CURLY_BRACKET = "{";
    public static final String CLOSE_CURLY_BRACKET = "}";
    /*Controller*/
    public static final String SAVE = "save";
    public static final String UPDATE = "update";
    public static final String ADDITIONAL_CHARGE = "additionalCharge";
    public static final String PAYMENT = "payment";
    public static final String BREAKDOWN = "breakdown";
    public static final String HISTORY = "history";
    public static final String PATIENTS_WITH_RECORDS = "patientsWithRecords";
    /*Pagination*/
    public static final String SEARCH_ALL_COLUMNS = "searchAllColumns";
    public static final String ASC = "ASC";
    /*Date Time Format AND Zone*/
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_ZONE = "GMT+8";
    /*Columns and Implementation*/
    public static final String CREATEDDATE = "createdDate";
    public static final String BILL_NAME = "billName";
    public static final String TOTAL_BILL = "totalBill";
    public static final String TOTAL_BALANCE = "totalBalance";
    public static final String PROFILE_ID = "profileId";
    public static final String BILL_ID = "billId";
    public static final String CHARGE_ID = "chargeId";
    public static final String PAYMENT_ID = "paymentId";
    public static final String REASON_CHANGED = "reasonChanged";
    public static final String CHARGE_REASON_CHANGE = "chargeReasonChange";
    public static final String CREATED_BY_NAME = "createdByName";
    public static final String ADDITIONAL_CHARGE_AMOUNT = "additionalChargeAmount";
    public static final String PAYMENT_AMOUNT = "paymentAmount";
    public static final String PAYMENT_NOTE = "paymentNote";
    public static final String PAYMENT_REASON_CHANGE = "paymentReasonChange";
}
