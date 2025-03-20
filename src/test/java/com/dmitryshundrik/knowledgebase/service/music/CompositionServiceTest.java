package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.entity.music.Album;
import com.dmitryshundrik.knowledgebase.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.dto.music.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dmitryshundrik.knowledgebase.util.DtoCreator.getCompositionCreateEditDTO;
import static com.dmitryshundrik.knowledgebase.util.EntityCreator.getAlbum;
import static com.dmitryshundrik.knowledgebase.util.EntityCreator.getComposition;
import static com.dmitryshundrik.knowledgebase.util.EntityCreator.getMusician;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompositionServiceTest {

    @Mock
    private CompositionRepository compositionRepository;

    @InjectMocks
    private CompositionService compositionService;

    private Musician musician;

    private Album album;

    private Composition composition;

    @BeforeEach
    void setUp() {
        musician = getMusician();
        album = getAlbum();
        composition = getComposition();
    }

    @Test
    void getCompositionBySlug_successfullyTest() {
        when(compositionRepository.findCompositionBySlug(composition.getSlug())).thenReturn(composition);

        Composition retrievedComposition = compositionService.getCompositionBySlug(composition.getSlug());

        verify(compositionRepository).findCompositionBySlug(composition.getSlug());

        assertEquals(composition, retrievedComposition);
    }

    @Test
    void createCompositionTest_successfullyTest() {
        CompositionCreateEditDTO compositionCreateEditDTO = getCompositionCreateEditDTO();
        Musician musician = getMusician();

        when(compositionRepository.save(any(Composition.class))).thenAnswer(i -> i.getArguments()[0]);

        CompositionViewDTO createdComposition = compositionService.createComposition(compositionCreateEditDTO, musician, null);

        verify(compositionRepository).save(any(Composition.class));

        assertEquals(compositionCreateEditDTO.getTitle(), createdComposition.getTitle());
    }
}

