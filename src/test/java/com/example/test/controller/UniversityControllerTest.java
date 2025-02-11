package com.example.test.controller;

import com.example.test.dto.UniversityDto;
import com.example.test.service.UniversityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UniversityControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UniversityService universityService;
    @InjectMocks
    private UniversityController universityController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(universityController).build();
    }

    @Test
    public void UniversityController_CreateUniversity() throws Exception {
        UniversityDto universityDto = UniversityDto.builder()
                .id(1L)
                .name("ITMO")
                .website("itmo.ru")
                .build();

        given(universityService.create(ArgumentMatchers.any())).willReturn(universityDto);

        ResultActions response = mockMvc.perform(post("/universities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(universityDto)));

        response.andExpect(status().isCreated());
    }
}
