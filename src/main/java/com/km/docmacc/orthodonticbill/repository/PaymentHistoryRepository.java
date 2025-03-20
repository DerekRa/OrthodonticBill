package com.km.docmacc.orthodonticbill.repository;

import com.km.docmacc.orthodonticbill.model.entity.AdditionalChargeHistory;
import com.km.docmacc.orthodonticbill.model.entity.BillChangedHistory;
import com.km.docmacc.orthodonticbill.model.entity.OrthoBill;
import com.km.docmacc.orthodonticbill.model.entity.PaymentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByTransactionId(Long transactionId);
    Page<PaymentHistory> findByTransactionId(Long transactionId, Pageable paging);
    Page<PaymentHistory> findByTransactionIdAndPaymentAmount(Long transactionId, Double paymentAmount, Pageable paging);
    Page<PaymentHistory> findByTransactionIdAndPaymentNoteLike(Long transactionId, String paymentNote, Pageable paging);
    Page<PaymentHistory> findByTransactionIdAndPaymentReasonChangeLike(Long transactionId, String paymentReasonChange, Pageable paging);
    Page<PaymentHistory> findByTransactionIdAndCreatedDate(Long transactionId, LocalDate createdDate, Pageable paging);
    Page<PaymentHistory> findByTransactionIdAndCreatedByNameLike(Long transactionId, String createdByName, Pageable paging);
    @Query("FROM PaymentHistory i WHERE i.transactionId = :transactionId AND " +
            "(i.paymentNote like :columnValue OR i.paymentReasonChange like :columnValue OR i.createdByName like :columnValue)")
    Page<PaymentHistory> findByPaymentHistory(@Param("transactionId") Long transactionId, @Param("columnValue") String columnValue, Pageable paging);

}
