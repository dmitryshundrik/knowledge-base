package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDto;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.service.music.impl.CompositionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dmitryshundrik.knowledgebase.util.DtoCreator.getCompositionCreateEditDTO;
import static com.dmitryshundrik.knowledgebase.util.EntityCreator.getComposition;
import static com.dmitryshundrik.knowledgebase.util.EntityCreator.getMusician;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositionServiceTest {

    @Mock
    private CompositionRepository compositionRepository;

    @InjectMocks
    private CompositionServiceImpl compositionService;

    private Composition composition;

    @BeforeEach
    void setUp() {
        composition = getComposition();
    }

    @Test
    void getCompositionBySlug_successfullyTest() {
        when(compositionRepository.findBySlug(composition.getSlug())).thenReturn(composition);

        Composition retrievedComposition = compositionService.getBySlug(composition.getSlug());

        verify(compositionRepository).findBySlug(composition.getSlug());

        assertEquals(composition, retrievedComposition);
    }

    @Test
    void createCompositionTest_successfullyTest() {
        CompositionCreateEditDto compositionCreateEditDTO = getCompositionCreateEditDTO();
        Musician musician = getMusician();

        when(compositionRepository.save(any(Composition.class))).thenAnswer(i -> i.getArguments()[0]);

        Composition createdComposition = compositionService.createComposition(compositionCreateEditDTO, musician, null);

        verify(compositionRepository).save(any(Composition.class));

        assertEquals(compositionCreateEditDTO.getTitle(), createdComposition.getTitle());
    }
}

