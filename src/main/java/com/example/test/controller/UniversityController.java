package com.example.test.controller;

import com.example.test.dto.FieldAndCodeDto;
import com.example.test.dto.FieldDto;
import com.example.test.dto.UniversityAndFieldDto;
import com.example.test.dto.UniversityDto;
import com.example.test.service.CodeService;
import com.example.test.service.FieldService;
import com.example.test.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Swagger path: http://localhost:8080/swagger-ui/index.html#/

@AllArgsConstructor
@RestController
@RequestMapping("/universities")
public class UniversityController {
    private UniversityService universityService;
    private FieldService fieldService;
    private CodeService codeService;

    @Operation(summary = "Create university",
            description = "Take DTO, save in bd and return saved object.")
    @PostMapping
    public ResponseEntity<UniversityDto> createUniversity(@RequestBody UniversityDto universityDto){
        UniversityDto university = universityService.create(universityDto);
        return new ResponseEntity<>(university, HttpStatus.CREATED);
    }

    @Operation(summary = "Open university page by id",
            description = "Take id, find university with such id, find fields of studying for this university, find code for each field and build full object.")
    @GetMapping("/page/{id}")
    public ResponseEntity<UniversityAndFieldDto> getUniversityById(@PathVariable("id") Long universityId){
        // Получаем универы по id
        UniversityDto university = universityService.getById(universityId);

        // Получаем направления для универа
        List<FieldDto> fieldDtos = fieldService.getFieldByUniversityId(universityId);

        // Получаем код направления
        List<FieldAndCodeDto> fieldAndCodeDtos = new ArrayList<>();
        for (FieldDto fieldDto: fieldDtos){
            fieldAndCodeDtos.add(new FieldAndCodeDto(fieldDto, codeService.getById(fieldDto.getCode())));
        }

        // Составляем объект с универом, направлением и кодом
        UniversityAndFieldDto universityAndFieldDto = new UniversityAndFieldDto(university, fieldAndCodeDtos);

        // Получить все направления у этого универа, в будущем добавить фильтр на направление
        return ResponseEntity.ok(universityAndFieldDto);
    }

    @Operation(summary = "Filter universities by field of studied code",
            description = "Take codeID, find fields for this codID, get universities for this fields and returns UniversityDTO list.")
    @GetMapping("{code}")
    public ResponseEntity<List<UniversityDto>> getUniversityByCodeFilter(@PathVariable("code") Long code){
        List<FieldDto> fields = fieldService.getFieldsByCode(code);
        List<Long> universityIds = fields.stream()
                .map(FieldDto::getUniversityId)
                .distinct()
                .toList();

        if (universityIds.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<UniversityDto> universities = universityService.getByIds(universityIds);
        return ResponseEntity.ok(universities);
    }

    @Operation(summary = "Open universities page. Get all universities.")
    @GetMapping
    public ResponseEntity<List<UniversityDto>> getAllUniversity(){
        List<UniversityDto> universityDtos = universityService.getAll();
        return ResponseEntity.ok(universityDtos);
    }

    @Operation(summary = "Update university by id")
    @PutMapping("{id}")
    public ResponseEntity<UniversityDto> updateUniversity(@PathVariable("id") Long universityId,
                                                          @RequestBody UniversityDto universityDto){
        UniversityDto universityDto1 = universityService.update(universityId, universityDto);
        return ResponseEntity.ok(universityDto1);
    }

    @Operation(summary = "Delete university by id")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUniversity(@PathVariable("id") Long universityId){
        universityService.delete(universityId);
        return ResponseEntity.ok("University deleted successfully");
    }
}
