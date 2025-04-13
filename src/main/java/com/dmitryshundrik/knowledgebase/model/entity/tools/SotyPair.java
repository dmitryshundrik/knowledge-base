package com.dmitryshundrik.knowledgebase.model.entity.tools;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import lombok.Data;

@Data
public class SotyPair {

    private Composition firstComposition;

    private Composition secondComposition;

    private boolean firstResult;

    private boolean secondResult;
}
