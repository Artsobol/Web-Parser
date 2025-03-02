package com.example.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldDto {
    private Long id;
    private Long code;
    private Long universityId;
    private Integer cost;
    private Integer freePlaceQuantity;
    private Integer paidPlaceQuantity;
    private Integer targetPlaceQuantity;
}
