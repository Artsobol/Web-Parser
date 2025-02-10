package com.example.test.mapper;


import com.example.test.dto.UniversityDto;
import com.example.test.entity.University;

public class UniversityMapper {

    public static UniversityDto maptoUniversityDto(University university){
        return UniversityDto.builder()
                .id(university.getId())
                .name(university.getName())
                .website(university.getWebsite()).build();
    }

    public static University mapToUniversity(UniversityDto universityDto){
        return new University(universityDto.getId(), universityDto.getName(), universityDto.getWebsite());
    }
}
