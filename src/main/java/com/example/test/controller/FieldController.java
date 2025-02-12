package com.example.test.controller;

import com.example.test.dto.FieldDto;
import com.example.test.service.FieldService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/fields")
public class FieldController {
    private FieldService fieldService;

    @Operation(summary = "Create field if study",
            description = "Take DTO, save it in bd and return saved object.")
    @PostMapping
    public ResponseEntity<FieldDto> createField(@RequestBody FieldDto fieldDto){
        FieldDto fieldDto1 = fieldService.create(fieldDto);
        return new ResponseEntity<>(fieldDto1, HttpStatus.CREATED);
    }
}
