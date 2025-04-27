package com.dmitryshundrik.knowledgebase.mapper.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class ProseMapper {

    @Mapping(target = "quoteList", ignore = true)
    public abstract Prose toProse(ProseCreateEditDto proseDto);

    @Mapping(target = "quoteList", ignore = true)
    public abstract void updateProse(@MappingTarget Prose prose, ProseCreateEditDto proseDto);

    @Mapping(target = "writerNickname", source = "prose.writer.nickName")
    @Mapping(target = "writerSlug", source = "prose.writer.slug")
    @Mapping(target = "quoteList", ignore = true)
    public abstract ProseViewDto toProseViewDto(@MappingTarget ProseViewDto proseDto, Prose prose);

    @AfterMapping
    public void toProseViewDtoPostProcess(@MappingTarget ProseViewDto proseDto, Prose prose) {
        Instant created = prose.getCreated();
        proseDto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);
    }

    @Mapping(target = "writerNickname", source = "prose.writer.nickName")
    @Mapping(target = "writerSlug", source = "prose.writer.slug")
    @Mapping(target = "quoteList", ignore = true)
    public abstract ProseCreateEditDto toProseCreateEditDto(@MappingTarget ProseCreateEditDto proseDto, Prose prose);
}
