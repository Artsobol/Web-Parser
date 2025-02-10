package com.example.test.service;

import com.example.test.dto.FieldDto;
import com.example.test.entity.Field;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.mapper.FieldMapper;
import com.example.test.repository.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FieldService implements IService<FieldDto> {
    private FieldRepository fieldRepository;
    @Override
    public FieldDto create(FieldDto dto) {
        Field field = FieldMapper.mapToField(dto);
        field.setLastUpdateDate(LocalDate.now());
        Field savedField = fieldRepository.save(field);
        return FieldMapper.mapToFieldDto(savedField);
    }

    @Override
    public FieldDto getById(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("University doesn't exist with this id: " + id));
        return FieldMapper.mapToFieldDto(field);
    }

    @Override
    public List<FieldDto> getAll() {
        List<Field> fieldList = fieldRepository.findAll();
        return fieldList.stream().map(FieldMapper::mapToFieldDto).collect(Collectors.toList());
    }

    @Override
    public FieldDto update(Long Id, FieldDto updated) {
        Field field = fieldRepository.findById(Id).orElseThrow(() ->
                new ResourceNotFoundException("University isn't found with this id: " + Id));
        field.setCode(updated.getCode());
        field.setUniversityId(updated.getUniversityId());
        field.setCost(updated.getCost());
        field.setFreePlaceQuantity(updated.getFreePlaceQuantity());
        field.setPaidPlaceQuantity(updated.getPaidPlaceQuantity());
        field.setTargetPlaceQuantity(updated.getTargetPlaceQuantity());
        field.setLastUpdateDate(LocalDate.now());

        Field field2 = fieldRepository.save(field);
        return FieldMapper.mapToFieldDto(field2);
    }

    @Override
    public void delete(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("University isn't found with this id: " + id));

        fieldRepository.deleteById(id);
    }

    public List<FieldDto> getFieldByUniversityId(Long universityId){
        List<Field> fieldList = fieldRepository.findByUniversityId(universityId);
        return fieldList.stream().map(FieldMapper::mapToFieldDto).collect(Collectors.toList());
    }

    public List<FieldDto> getFieldsByCode(Long code){
        List<Field> fields = fieldRepository.findByCode(code);
        return fields.stream()
                .map(field -> FieldDto.builder()
                        .id(field.getId())
                        .code(field.getCode())
                        .universityId(field.getUniversityId())
                        .cost(field.getCost())
                        .freePlaceQuantity(field.getFreePlaceQuantity())
                        .paidPlaceQuantity(field.getPaidPlaceQuantity())
                        .targetPlaceQuantity(field.getTargetPlaceQuantity()).build())
                .toList();
    }
}
