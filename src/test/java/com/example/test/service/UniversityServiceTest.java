package com.example.test.service;

import com.example.test.dto.UniversityDto;
import com.example.test.entity.University;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.repository.UniversityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniversityServiceTest {
    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UniversityService universityService;

    @Test
    public void UniversityService_Create_ReturnCreatedUniversity(){
        University university = new University("ITMO", "site");
        UniversityDto universityDto = UniversityDto.builder().name("ITMO").website("site").build();
        when(universityRepository.save(Mockito.any(University.class))).thenReturn(university);

        UniversityDto savedUniversityDto = universityService.create(universityDto);

        Assertions.assertThat(savedUniversityDto).isEqualTo(universityDto);
    }

    @Test
    public void UniversityService_GetById_ReturnRightUniversity(){
        University university = new University("ITMO", "site");
        UniversityDto universityDto2 = UniversityDto.builder().name("ITMO").website("site").build();
        universityRepository.save(university);
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));

        UniversityDto universityDto = universityService.getById(1L);

        Assertions.assertThat(universityDto).isEqualTo(universityDto2);
    }

    @Test
    public void UniversityService_GetById_ReturnError(){
        when(universityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> universityService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("University doesn't exist with this id: 1");
    }


    @Test
    public void UniversityService_GetAll_ReturnRightUniversities(){
        University university = new University("ITMO", "site");
        University university2 = new University("ITMO2", "site2");
        UniversityDto universityDto2 = UniversityDto.builder().name("ITMO").website("site").build();
        UniversityDto universityDto3 = UniversityDto.builder().name("ITMO2").website("site2").build();
        universityRepository.save(university);
        universityRepository.save(university2);
        when(universityRepository.findAll()).thenReturn(Arrays.asList(university, university2));

        List<UniversityDto> universityDto = universityService.getAll();

        Assertions.assertThat(universityDto).isEqualTo(Arrays.asList(universityDto2, universityDto3));
    }

    @Test
    public void UniversityService_Delete_DeletingUniversity(){
        University university = new University("ITMO", "site");
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));

        assertAll(() -> universityService.delete(1L));
    }

    @Test
    public void UniversityService_Update_ReturnError(){
        when(universityRepository.findById(1L)).thenReturn(Optional.empty());
        UniversityDto universityDto2 = UniversityDto.builder().name("ITMO").website("site").build();

        assertThatThrownBy(() -> universityService.update(1L, universityDto2))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("University isn't found with this id: 1");
    }

    @Test
    public void UniversityService_Delete_ReturnError(){
        when(universityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> universityService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("University isn't found with this id: 1");
    }
}
