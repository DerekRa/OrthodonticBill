package com.km.docmacc.orthodonticbill.repository;

import com.km.docmacc.orthodonticbill.model.entity.BillChangedHistory;
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
public interface BillChangedHistoryRepository extends JpaRepository<BillChangedHistory, Long> {
    List<BillChangedHistory> findByOrthoBillId(Long orthoBillId);
    Page<BillChangedHistory> findByOrthoBillId(Long orthoBillId, Pageable paging);
    Page<BillChangedHistory> findByOrthoBillIdAndBillNameLike(Long orthoBillId, String billName, Pageable paging);
    Page<BillChangedHistory> findByOrthoBillIdAndTotalBill(Long orthoBillId, Double totalBill, Pageable paging);
    Page<BillChangedHistory> findByOrthoBillIdAndCreatedDate(Long orthoBillId, LocalDate createdDate, Pageable paging);
    Page<BillChangedHistory> findByOrthoBillIdAndReasonChangedLike(Long orthoBillId, String reasonChanged, Pageable paging);
    Page<BillChangedHistory> findByOrthoBillIdAndCreatedByNameLike(Long orthoBillId, String createdByName, Pageable paging);
    @Query("FROM BillChangedHistory i WHERE i.orthoBillId = :orthoBillId AND " +
            "(i.billName like :columnValue " +
            "OR i.reasonChanged like :columnValue " +
            "OR i.createdByName like :columnValue)")
    Page<BillChangedHistory> findByBillChangesHistory(@Param("orthoBillId") Long orthoBillId, @Param("columnValue") String columnValue, Pageable paging);
}
