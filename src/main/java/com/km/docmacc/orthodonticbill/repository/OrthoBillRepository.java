package com.km.docmacc.orthodonticbill.repository;

import com.km.docmacc.orthodonticbill.model.entity.OrthoBill;
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
public interface OrthoBillRepository extends JpaRepository<OrthoBill, Long> {
    Optional<OrthoBill> findByIdAndProfileId(Long billId, Long profileId);
    List<OrthoBill> findByProfileId(Long profileId);
    Page<OrthoBill> findByProfileId(Long profileId, Pageable paging);
    Page<OrthoBill> findAllByProfileIdAndCreatedDate(Long profileId, LocalDate createdDate, Pageable paging);
}
