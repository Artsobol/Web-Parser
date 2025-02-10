package com.example.test.repository;

import com.example.test.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByIdIn(List<Long> ids);
}
