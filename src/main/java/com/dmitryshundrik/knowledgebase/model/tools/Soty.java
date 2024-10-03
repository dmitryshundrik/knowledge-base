package com.dmitryshundrik.knowledgebase.model.tools;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Soty {

    private Integer year;

    private List<SotyPair> pairList;

    private List<Map.Entry<Composition, Double>> top;
}
