package com.dmitryshundrik.knowledgebase.entity.tools;

import com.dmitryshundrik.knowledgebase.entity.music.Composition;
import lombok.Data;

@Data
public class SotyPair {

    private Composition firstComposition;

    private Composition secondComposition;

    private boolean firstResult;

    private boolean secondResult;
}
