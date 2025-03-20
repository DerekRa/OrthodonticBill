package com.km.docmacc.orthodonticbill.repository;

import com.km.docmacc.orthodonticbill.model.entity.OrthoBill;
import com.km.docmacc.orthodonticbill.model.entity.PayOrChargeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayOrChargeTransactionRepository extends JpaRepository<PayOrChargeTransaction, Long> {
    List<PayOrChargeTransaction> findByOrthoBillId(Long orthoBillId);
    Page<PayOrChargeTransaction> findByOrthoBillId(Long orthoBillId, Pageable paging);
    Page<PayOrChargeTransaction> findByOrthoBillIdAndPaymentNoteLike(Long orthoBillId, String paymentNote, Pageable paging);
    @Query("FROM PayOrChargeTransaction i WHERE i.orthoBillId = :orthoBillId AND " +
            "(i.additionalChargeAmount = :columnValue OR i.paymentAmount = :columnValue OR i.paymentNote like '%:columnValue%')")
    Page<PayOrChargeTransaction> findByTransaction(@Param("orthoBillId") Long orthoBillId, @Param("columnValue") String columnValue, Pageable paging);
}
