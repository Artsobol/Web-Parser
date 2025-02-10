package com.example.test.service;

import com.example.test.dto.UniversityDto;
import com.example.test.entity.University;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.mapper.UniversityMapper;
import com.example.test.repository.UniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UniversityService implements IService<UniversityDto>{
    private UniversityRepository universityRepository;
    @Override
    public UniversityDto create(UniversityDto dto) {
        University university = UniversityMapper.mapToUniversity(dto);
        University savedUniversity = universityRepository.save(university);
        return UniversityMapper.maptoUniversityDto(savedUniversity);
    }

    @Override
    public UniversityDto getById(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("University doesn't exist with this id: " + id));
        return UniversityMapper.maptoUniversityDto(university);
    }

    @Override
    public List<UniversityDto> getAll() {
        List<University> universityList = universityRepository.findAll();
        return universityList.stream().map(UniversityMapper::maptoUniversityDto).collect(Collectors.toList());
    }

    @Override
    public UniversityDto update(Long Id, UniversityDto updated) {
        University university = universityRepository.findById(Id).orElseThrow(() ->
                new ResourceNotFoundException("University isn't found with this id: " + Id));
        university.setName(updated.getName());
        university.setWebsite(updated.getWebsite());

        University university2 = universityRepository.save(university);
        return UniversityMapper.maptoUniversityDto(university2);
    }

    @Override
    public void delete(Long id) {
        University university = universityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("University isn't found with this id: " + id));

        universityRepository.deleteById(id);
    }

    public List<UniversityDto> getByIds(List<Long> universityIds){
        List<University> universities = universityRepository.findByIdIn(universityIds);
        return universities.stream()
                .map(UniversityMapper::maptoUniversityDto)
                .toList();
    }
}
