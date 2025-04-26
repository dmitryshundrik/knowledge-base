package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;

public interface ProseService extends BaseService {

    Prose getBySlug(String proseSlug);
    Prose getById(String id);

    List<Prose> getAll();
    List<Prose> getAllSortedByCreatedDesc();
    List<Prose> getAllByWriter(Writer writer);
    List<Prose> getFirst5ByWriterSortedByRating(Writer writer);
    List<Prose> getLatestUpdate();

    Prose createProse(Writer writer, ProseCreateEditDto proseDto);
    ProseViewDto updateProse(Prose prose, ProseCreateEditDto proseDto);
    void deleteProse(String proseSlug);

    void updateSynopsisSchemaBySlug(String proseSlug, byte[] bytes);
    void deleteSynopsisSchema(String proseSlug);

    ProseViewDto getProseViewDto(Prose prose);
    List<ProseViewDto> getProseViewDtoList(List<Prose> proseList);
    ProseCreateEditDto getProseCreateEditDto(Prose prose);
    ProseSelectDto getProseSelectDto(Prose prose);
    List<ProseSelectDto> getProseSelectDtoList(List<Prose> proseList);
}
