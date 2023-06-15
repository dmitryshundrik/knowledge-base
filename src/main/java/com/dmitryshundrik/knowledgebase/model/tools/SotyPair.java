package com.dmitryshundrik.knowledgebase.model.tools;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import lombok.Data;

@Data
public class SotyPair {

    private Composition firstComposition;

    private Composition secondComposition;

    private boolean firstResult;

    private boolean secondResult;

}
