package com.dmitryshundrik.knowledgebase.model.entity.tools;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Soty {

    private Integer year;

    private List<SotyPair> pairList;

    private List<Map.Entry<Composition, Double>> top;
}
