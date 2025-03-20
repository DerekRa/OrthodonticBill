package com.km.docmacc.orthodonticbill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationData {
    private Long id;
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String orderBy;
    private String findItem;
}
