package com.example.test.service;

import com.example.test.dto.FieldDto;
import com.example.test.entity.Field;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.repository.FieldRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FieldServiceTest {
    @Mock
    private FieldRepository fieldRepository;

    @InjectMocks
    private FieldService fieldService;

    @Test
    public void FieldService_Create_ReturnCreatedField(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();
        when(fieldRepository.save(Mockito.any(Field.class))).thenReturn(field);

        FieldDto savedFieldDto = fieldService.create(fieldDto);

        Assertions.assertThat(savedFieldDto).isEqualTo(fieldDto);
    }

    @Test
    public void FieldService_GetById_ReturnRightField(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();
        fieldRepository.save(field);
        when(fieldRepository.findById(1L)).thenReturn(Optional.of(field));

        FieldDto fieldDto1 = fieldService.getById(1L);

        Assertions.assertThat(fieldDto1).isEqualTo(fieldDto);
    }

    @Test
    public void FieldService_GetById_ReturnError(){
        when(fieldRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fieldService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("University doesn't exist with this id: 1");
    }

    @Test
    public void FieldService_GetAll_ReturnRightFields(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        Field field2 = new Field(2L, 1L, 1L, 300, 3, 5, 6);
        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();
        FieldDto fieldDto2 = FieldDto.builder()
                .id(2L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();
        fieldRepository.save(field);
        fieldRepository.save(field2);

        when(fieldRepository.findAll()).thenReturn(Arrays.asList(field, field2));

        List<FieldDto> fieldDtos = fieldService.getAll();

        Assertions.assertThat(fieldDtos).isEqualTo(Arrays.asList(fieldDto, fieldDto2));
    }

    @Test
    public void FieldService_Delete_DeletingField(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        when(fieldRepository.findById(1L)).thenReturn(Optional.of(field));

        assertAll(() -> fieldService.delete(1L));
    }

    @Test
    public void FieldService_Update_ReturnError(){
        when(fieldRepository.findById(1L)).thenReturn(Optional.empty());
        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();

        assertThatThrownBy(() -> fieldService.update(1L, fieldDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("University isn't found with this id: 1");
    }

    @Test
    public void FieldService_Delete_ReturnError(){
        when(fieldRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fieldService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("University isn't found with this id: 1");
    }

    @Test
    public void FieldService_GetFieldByUniversityId_ReturnRightFields(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        Field field2 = new Field(2L, 1L, 1L, 300, 3, 5, 6);
        List<Field> fieldList = Arrays.asList(field, field2);
        when(fieldRepository.findByUniversityId(1L)).thenReturn(fieldList);

        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();
        FieldDto fieldDto2 = FieldDto.builder()
                .id(2L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();

        List<FieldDto> fieldDtos = fieldService.getFieldByUniversityId(1L);

        Assertions.assertThat(fieldDtos).isEqualTo(Arrays.asList(fieldDto, fieldDto2));
    }

    @Test
    public void FieldService_GetFieldByCode_ReturnRightFields(){
        Field field = new Field(1L, 1L, 1L, 300, 3, 5, 6);
        Field field2 = new Field(2L, 1L, 1L, 300, 3, 5, 6);
        List<Field> fieldList = Arrays.asList(field, field2);
        when(fieldRepository.findByCode(1L)).thenReturn(fieldList);

        FieldDto fieldDto = FieldDto.builder()
                .id(1L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();
        FieldDto fieldDto2 = FieldDto.builder()
                .id(2L)
                .code(1L)
                .universityId(1L)
                .cost(300)
                .freePlaceQuantity(3)
                .paidPlaceQuantity(5)
                .targetPlaceQuantity(6).build();

        List<FieldDto> fieldDtos = fieldService.getFieldsByCode(1L);

        Assertions.assertThat(fieldDtos).isEqualTo(Arrays.asList(fieldDto, fieldDto2));
    }
}
