package com.example.test.mapper;

import com.example.test.dto.UniversityDto;
import com.example.test.entity.University;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UniversityMapperTest {

    @Test
    public void UniversityMapper_MapToUniversityDto(){
        University university = new University("ITMO", "site");
        UniversityDto universityDto2 = UniversityDto.builder().name("ITMO").website("site").build();

        UniversityDto universityDto = UniversityMapper.maptoUniversityDto(university);

        Assertions.assertThat(universityDto).isEqualTo(universityDto2);
    }

    @Test
    public void UniversityMapper_MapToUniversity(){
        University university = new University("ITMO", "site");
        UniversityDto universityDto2 = UniversityDto.builder().name("ITMO").website("site").build();

        University university2 = UniversityMapper.mapToUniversity(universityDto2);

        Assertions.assertThat(university2.getName()).isEqualTo(university.getName());
        Assertions.assertThat(university2.getWebsite()).isEqualTo(university.getWebsite());
        Assertions.assertThat(university2.getId()).isEqualTo(university.getId());
    }
}
