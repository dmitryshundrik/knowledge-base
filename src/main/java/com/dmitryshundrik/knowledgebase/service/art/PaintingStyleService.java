package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.repository.art.PaintingStyleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaintingStyleService {

    private final PaintingStyleRepository paintingStyleRepository;

    public PaintingStyleService(PaintingStyleRepository paintingStyleRepository) {
        this.paintingStyleRepository = paintingStyleRepository;
    }
}
