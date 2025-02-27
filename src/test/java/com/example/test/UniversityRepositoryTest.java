package com.example.test;

import com.example.test.entity.University;
import com.example.test.repository.UniversityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UniversityRepositoryTest {
    @Autowired
    private UniversityRepository universityRepository;

    @Test
    public void UniversityRepository_Save_ReturnSavedUniversity(){
        University university = new University("ITMO", "site");

        University savedUniversity = universityRepository.save(university);

        Assertions.assertThat(savedUniversity).isNotNull();
        Assertions.assertThat(savedUniversity.getId()).isGreaterThan(0);
    }

    @Test
    public void UniversityRepository_GetAll_ReturnMoreThanOneUniversity(){
        University university = new University("ITMO", "site");
        University university2 = new University("ITMO2", "site2");
        universityRepository.save(university);
        universityRepository.save(university2);

        List<University> universityList = universityRepository.findAll();

        Assertions.assertThat(universityList).isNotNull();
        Assertions.assertThat(universityList.size()).isEqualTo(2);
    }

    @Test
    public void UniversityRepository_GetById_ReturnRightUniversity(){
        University university = new University("ITMO", "site");
        University university2 = new University("ITMO2", "site2");
        University savedUniversity = universityRepository.save(university);
        universityRepository.save(university2);

        Optional<University> universityChecked = universityRepository.findById(savedUniversity.getId());

        Assertions.assertThat(universityChecked).isNotEmpty();
        Assertions.assertThat(universityChecked.get().getId()).isEqualTo(savedUniversity.getId());
    }

    @Test
    public void UniversityRepository_Delete_DeletingUniversity(){
        University university = new University("ITMO", "site");
        universityRepository.save(university);

        universityRepository.deleteById(1L);
        Optional<University> universityChecked = universityRepository.findById(1L);

        Assertions.assertThat(universityChecked).isEmpty();
    }

    @Test
    public void UniversityRepository_FindByIdIn_ReturnRightUniversities(){
        University university = new University("ITMO", "site");
        University university2 = new University("ITMO2", "site2");
        University university3 = new University("ITMO3", "site3");
        University savedUniversity = universityRepository.save(university);
        University savedUniversity2 = universityRepository.save(university2);
        University savedUniversity3 = universityRepository.save(university3);
        List<Long> ids = Arrays.asList(savedUniversity.getId(), savedUniversity2.getId());

        List<University> universityList = universityRepository.findByIdIn(ids);

        Assertions.assertThat(universityList.size()).isEqualTo(2);
        Assertions.assertThat(universityList.get(0)).isEqualTo(university);
    }
}
