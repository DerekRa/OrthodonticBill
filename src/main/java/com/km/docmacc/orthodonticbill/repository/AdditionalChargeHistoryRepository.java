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
public interface AdditionalChargeHistoryRepository extends JpaRepository<AdditionalChargeHistory, Long> {
    List<AdditionalChargeHistory> findByTransactionId(Long transactionId);
    Page<AdditionalChargeHistory> findByTransactionId(Long transactionId, Pageable paging);
    Page<AdditionalChargeHistory> findByTransactionIdAndAdditionalChargeAmount(Long transactionId, Double additionalChargeAmount, Pageable paging);
    Page<AdditionalChargeHistory> findByTransactionIdAndCreatedDate(Long transactionId, LocalDate createdDate, Pageable paging);
    Page<AdditionalChargeHistory> findByTransactionIdAndChargeReasonChangeLike(Long transactionId, String chargeReasonChange, Pageable paging);
    Page<AdditionalChargeHistory> findByTransactionIdAndCreatedByNameLike(Long transactionId, String createdByName, Pageable paging);
    @Query("FROM AdditionalChargeHistory i WHERE i.transactionId = :transactionId AND " +
            "(i.chargeReasonChange like :columnValue OR i.createdByName like :columnValue)")
    Page<AdditionalChargeHistory> findByAdditionalChargeHistory(@Param("transactionId") Long transactionId, @Param("columnValue") String columnValue, Pageable paging);
}
