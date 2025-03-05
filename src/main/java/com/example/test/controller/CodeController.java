package com.example.test.controller;

import com.example.test.dto.CodeDto;
import com.example.test.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/code")
public class CodeController {
    private CodeService codeService;

    @Operation(summary = "Open codes page. Get all codes.")
    @GetMapping
    public ResponseEntity<List<CodeDto>> getAllCode(){
        List<CodeDto> codeDtos = codeService.getAll();
        return ResponseEntity.ok(codeDtos);
    }
}
