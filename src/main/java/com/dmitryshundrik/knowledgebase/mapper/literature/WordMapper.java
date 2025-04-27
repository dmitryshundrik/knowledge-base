package com.dmitryshundrik.knowledgebase.mapper.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class WordMapper {

    public abstract WordDto toWordDto(@MappingTarget WordDto wordDto, Word word);

    @AfterMapping
    public  void toWordDtoPostProcess(@MappingTarget WordDto wordDto, Word word) {
        Instant created = word.getCreated();
        wordDto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);
    }
}
