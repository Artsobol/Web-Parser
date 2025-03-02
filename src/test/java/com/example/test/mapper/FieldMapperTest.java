package com.example.test.mapper;

import com.example.test.dto.FieldDto;
import com.example.test.entity.Field;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldMapperTest {
    @Test
    public void FieldMapper_mapToFieldDto_MapToDto(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();

        FieldDto fieldDto2 = FieldMapper.mapToFieldDto(field);

        Assertions.assertThat(fieldDto2).isEqualTo(fieldDto);
    }

    @Test
    public void FieldMapper_mapToField(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();

        Field field2 = FieldMapper.mapToField(fieldDto);

        Assertions.assertThat(field2).isEqualTo(field);
    }
}
