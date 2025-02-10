package com.example.test.mapper;


import com.example.test.dto.FieldDto;
import com.example.test.entity.Field;

public class FieldMapper {

    public static FieldDto mapToFieldDto(Field field){
        return FieldDto.builder()
                .id(field.getId())
                .code(field.getCode())
                .universityId(field.getUniversityId())
                .cost(field.getCost())
                .freePlaceQuantity(field.getFreePlaceQuantity())
                .paidPlaceQuantity(field.getPaidPlaceQuantity())
                .targetPlaceQuantity(field.getTargetPlaceQuantity())
                .build();
    }

    public static Field mapToField(FieldDto fieldDto){
        return new Field(fieldDto.getId(),
                fieldDto.getCode(),
                fieldDto.getUniversityId(),
                fieldDto.getCost(),
                fieldDto.getFreePlaceQuantity(),
                fieldDto.getPaidPlaceQuantity(),
                fieldDto.getTargetPlaceQuantity());
    }
}
