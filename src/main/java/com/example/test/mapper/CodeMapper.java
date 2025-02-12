package com.example.test.mapper;

import com.example.test.dto.CodeDto;
import com.example.test.entity.Code;

public class CodeMapper {
    public static CodeDto mapToCodeDto(Code code){
        return new CodeDto(code.getId(), code.getTitle(), code.getDescription());
    }

    public static Code mapToCode(CodeDto codeDto){
        return new Code(codeDto.getId(), codeDto.getTitle(), codeDto.getDescription());
    }
}
