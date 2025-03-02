package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UniversityAndFieldDto {
    private UniversityDto university;
    private List<FieldAndCodeDto> fieldAndCodeDtos;
}