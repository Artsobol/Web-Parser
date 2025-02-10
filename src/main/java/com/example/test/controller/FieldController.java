package com.example.test.controller;

import com.example.test.dto.FieldDto;
import com.example.test.service.FieldService;
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

    @PostMapping
    public ResponseEntity<FieldDto> createField(@RequestBody FieldDto fieldDto){
        FieldDto fieldDto1 = fieldService.create(fieldDto);
        return new ResponseEntity<>(fieldDto1, HttpStatus.CREATED);
    }
}
