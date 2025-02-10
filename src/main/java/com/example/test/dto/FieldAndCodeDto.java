package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldAndCodeDto {
    private FieldDto fieldDto;
    private CodeDto codeDto;
}
