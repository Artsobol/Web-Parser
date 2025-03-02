package com.example.test.mapper;

import com.example.test.dto.CodeDto;
import com.example.test.entity.Code;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodeMapperTest {
    @Test
    public void CodeMapper_mapToCodeDto(){
        Code code = new Code(1L, "title", "description");
        CodeDto codeDto = new CodeDto(1L, "title", "description");

        CodeDto codeDto2 = CodeMapper.mapToCodeDto(code);

        Assertions.assertThat(codeDto2).isEqualTo(codeDto);
    }

    @Test
    public void CodeMapper_mapToCode(){
        Code code = new Code(1L, "title", "description");
        CodeDto codeDto = new CodeDto(1L, "title", "description");

        Code code2 = CodeMapper.mapToCode(codeDto);

        Assertions.assertThat(code2).isEqualTo(code);
    }
}
