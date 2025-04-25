package com.dmitryshundrik.knowledgebase.mapper.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class WriterMapper {

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "proseList", ignore = true)
    @Mapping(target = "quoteList", ignore = true)
    @Mapping(target = "wordList", ignore = true)
    public abstract Writer toWriter(WriterCreateEditDto writerDto);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "proseList", ignore = true)
    @Mapping(target = "quoteList", ignore = true)
    @Mapping(target = "wordList", ignore = true)
    public abstract void updateWriter(@MappingTarget Writer writer, WriterCreateEditDto writerDto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "proseList", ignore = true)
    @Mapping(target = "quoteList", ignore = true)
    @Mapping(target = "wordList", ignore = true)
    public abstract WriterViewDto toWriterViewDto(@MappingTarget WriterViewDto writerDto, Writer writer);

    @AfterMapping
    public void toWriterViewDtoPostProcess(@MappingTarget WriterViewDto writerDto, Writer writer) {
        Instant created = writer.getCreated();
        writerDto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);
    }

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "proseList", ignore = true)
    @Mapping(target = "quoteList", ignore = true)
    @Mapping(target = "wordList", ignore = true)
    public abstract WriterCreateEditDto toWriterCreateEditDto(@MappingTarget WriterCreateEditDto writerDto, Writer writer);
}
