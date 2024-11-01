package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.repository.art.PaintingStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaintingStyleService {

    private final PaintingStyleRepository paintingStyleRepository;
}
