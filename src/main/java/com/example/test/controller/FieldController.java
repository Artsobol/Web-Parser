package com.example.test.controller;

import com.example.test.dto.CodeDto;
import com.example.test.dto.FieldAndCodeDto;
import com.example.test.dto.FieldDto;
import com.example.test.service.CodeService;
import com.example.test.service.FieldService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/fields")
public class FieldController {
    private FieldService fieldService;
    private CodeService codeService;

    @Operation(summary = "Get all fields with code")
    @GetMapping
    public ResponseEntity<List<FieldAndCodeDto>> getAllFieldsWithCode(){
        List<FieldDto> fieldDtos = fieldService.getAll();
        List<CodeDto> codeDtos = codeService.getAll();
        Map<Long, CodeDto> codeDtoMap = codeDtos.stream()
                .collect(Collectors.toMap(CodeDto::getId, codeDto -> codeDto));

        List<FieldAndCodeDto> fieldAndCodeDtos = new ArrayList<>();
        for (FieldDto fieldDto: fieldDtos){
            Long codeId = fieldDto.getCode();
            CodeDto codeDto = codeDtoMap.get(codeId);
            fieldAndCodeDtos.add(new FieldAndCodeDto(fieldDto, codeDto));
        }

        return ResponseEntity.ok(fieldAndCodeDtos);
    }

    @Operation(summary = "Create field if study",
            description = "Take DTO, save it in bd and return saved object.")
    @PostMapping
    public ResponseEntity<FieldDto> createField(@RequestBody FieldDto fieldDto){
        FieldDto fieldDto1 = fieldService.create(fieldDto);
        return new ResponseEntity<>(fieldDto1, HttpStatus.CREATED);
    }
}
