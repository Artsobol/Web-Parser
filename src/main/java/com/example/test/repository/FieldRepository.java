package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test.entity.Field;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByUniversityId(Long universityId);
    List<Field> findByCode(Long code);
}
