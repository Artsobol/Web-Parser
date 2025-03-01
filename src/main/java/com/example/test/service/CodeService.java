package com.example.test.service;

import com.example.test.dto.CodeDto;
import com.example.test.entity.Code;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.mapper.CodeMapper;
import com.example.test.repository.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CodeService implements IService<CodeDto> {
    private CodeRepository codeRepository;

    @Override
    public CodeDto create(CodeDto dto) {
        Code code = CodeMapper.mapToCode(dto);
        Code savedCode = codeRepository.save(code);
        return CodeMapper.mapToCodeDto(savedCode);
    }

    @Override
    public CodeDto getById(Long id) {
        Code code = codeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("University doesn't exist with this id: " + id));
        return CodeMapper.mapToCodeDto(code);
    }

    @Override
    public List<CodeDto> getAll() {
        List<Code> codeList = codeRepository.findAll();
        return codeList.stream().map(CodeMapper::mapToCodeDto).collect(Collectors.toList());
    }

    @Override
    public CodeDto update(Long Id, CodeDto updated) {
        Code code = codeRepository.findById(Id).orElseThrow(() ->
                new ResourceNotFoundException("University isn't found with this id: " + Id));
        code.setTitle(updated.getTitle());

        Code code2 = codeRepository.save(code);
        return CodeMapper.mapToCodeDto(code2);
    }

    @Override
    public void delete(Long id) {
        Code code = codeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("University isn't found with this id: " + id));
        codeRepository.deleteById(id);
    }
}
