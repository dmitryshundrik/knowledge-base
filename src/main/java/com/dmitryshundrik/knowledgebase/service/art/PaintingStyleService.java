package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.repository.art.PaintingStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaintingStyleService {

    @Autowired
    private PaintingStyleRepository paintingStyleRepository;

}
