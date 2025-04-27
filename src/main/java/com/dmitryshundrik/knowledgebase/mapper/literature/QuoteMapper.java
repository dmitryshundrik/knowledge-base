package com.dmitryshundrik.knowledgebase.mapper.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class QuoteMapper {

    @Mapping(target = "writer", ignore = true)
    @Mapping(target = "prose", ignore = true)
    public abstract Quote toQuote(QuoteCreateEditDto quoteDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "writer", ignore = true)
    @Mapping(target = "prose", ignore = true)
    public abstract void updateQuote(@MappingTarget Quote quote, QuoteCreateEditDto quoteDto);

    @Mapping(target = "writerNickname", source = "quote.writer.nickName")
    @Mapping(target = "writerSlug", source = "quote.writer.slug")
    @Mapping(target = "proseTitle", source = "quote.prose.title")
    @Mapping(target = "proseSlug", source = "quote.prose.slug")
    public abstract QuoteViewDto toQuoteViewDto(@MappingTarget QuoteViewDto quoteDto, Quote quote);

    @AfterMapping
    public void toQuoteViewDtoPostProcess(@MappingTarget QuoteViewDto quoteDto, Quote quote) {
        Instant created = quote.getCreated();
        quoteDto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);
    }

    @Mapping(target = "writerNickname", source = "quote.writer.nickName")
    @Mapping(target = "writerSlug", source = "quote.writer.slug")
    @Mapping(target = "proseId", source = "quote.prose.id")
    public abstract QuoteCreateEditDto toQuoteCreateEditDto(@MappingTarget QuoteCreateEditDto quoteDto, Quote quote);
}
