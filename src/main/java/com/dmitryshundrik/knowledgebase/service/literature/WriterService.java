package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterEntityUpdateInfoDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterArchiveListDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.service.BaseService;

import java.util.List;
import java.util.Set;

public interface WriterService extends BaseService {

    Writer getBySlug(String writerSlug);

    List<WriterSimpleDto> getAllOrderByBorn();
    List<WriterArchiveListDto> getAllOrderByCreated();
    List<WriterEntityUpdateInfoDto> getLatestUpdate();

    Writer createWriter(WriterCreateEditDto writerDTO);
    WriterViewDto updateWriter(String writerSlug, WriterCreateEditDto writerDTO);
    void deleteWriter(String writerSlug);

    void updateWriterImage(String writerSlug, byte[] bytes);
    void deleteWriterImage(String writerSlug);

    WriterViewDto getWriterViewDto(Writer writer);
    WriterCreateEditDto getWriterCreateEditDto(Writer writer);

    Set<Writer> getAllWithCurrentBirth(Integer dayInterval);
    Set<Writer> getAllWithCurrentDeath(Integer dayInterval);
    Set<Writer> getAllWithCurrentBirthAndNotification(Integer dayInterval);
    Set<Writer> getAllWithCurrentDeathAndNotification(Integer dayInterval);
}
