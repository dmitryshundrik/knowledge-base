package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.tools.SotyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompositionSotyGeneratorService {

    private final CompositionService compositionService;

    public List<SotyPair> getPairListForSotyGenerator(Integer year) {
        List<Composition> compositionList = compositionService.getAllByYear(year);
        List<SotyPair> sotyPairList = new ArrayList<>();
        for (int i = 0; i < compositionList.size(); i++) {
            for (int j = i; j < compositionList.size() - 1; j++) {
                SotyPair pair = new SotyPair();
                pair.setFirstComposition(compositionList.get(i));
                pair.setSecondComposition(compositionList.get(j + 1));
                sotyPairList.add(pair);
            }
        }
        Collections.shuffle(sotyPairList);
        return sotyPairList;
    }

    public List<Map.Entry<Composition, Double>> getTopForSotyGenerator(List<SotyPair> staticPairList, List<SotyPair> pairList) {
        Map<Composition, Double> map = new HashMap<>();
        for (int i = 0; i < pairList.size(); i++) {
            if (pairList.get(i).isFirstResult()) {
                map.merge(staticPairList.get(i).getFirstComposition(), 1.0, Double::sum);
            }
            if (pairList.get(i).isSecondResult()) {
                map.merge(staticPairList.get(i).getSecondComposition(), 1.0, Double::sum);
            }
        }
        return map.entrySet()
                .stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).collect(Collectors.toList());
    }
}
