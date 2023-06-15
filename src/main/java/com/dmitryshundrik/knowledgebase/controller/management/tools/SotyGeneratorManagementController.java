package com.dmitryshundrik.knowledgebase.controller.management.tools;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.tools.Soty;
import com.dmitryshundrik.knowledgebase.model.tools.SotyPair;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class SotyGeneratorManagementController {

    @Autowired
    private CompositionService compositionService;

    private static List<SotyPair> staticPairList = new ArrayList<>();

    private static List<Map.Entry<Composition, Double>> result = new ArrayList<>();

    @GetMapping("/management/soty-generator")
    public String getSotyGeneretorYear(Model model) {
        model.addAttribute("years", compositionService.getAllYearsFromCompositions());
        model.addAttribute("soty", new Soty());
        return "management/tools/soty-generator-year";
    }

    @PostMapping("/management/soty-generator")
    public String postSotyGenerator(@ModelAttribute("soty") Soty soty) {
        Integer year = soty.getYear();
        return "redirect:/management/soty-generator/generate/" + year;
    }

    @GetMapping(("/management/soty-generator/generate/{year}"))
    public String getSotyGeneratorGenerate(@PathVariable Integer year, Model model) {
        List<Composition> compositionList = compositionService.getAllByYear(year);
        staticPairList = compositionService.getPairListForSotyGenerator(compositionList);
        Soty soty = new Soty();
        soty.setPairList(staticPairList);
        soty.setYear(year);
        model.addAttribute("soty", soty);
        return "management/tools/soty-generator-generate";
    }

    @PostMapping("/management/soty-generator/generate/{year}")
    public String postSotyGeneratorGenerate(@ModelAttribute("soty") Soty soty) {
        result = compositionService.getTopForSotyGenerator(staticPairList, soty.getPairList());
        return "redirect:/management/soty-generator/result";
    }

    @GetMapping("/management/soty-generator/result")
    public String getSotyGeneratorResult(Model model) {
        model.addAttribute("result", result);
        return "management/tools/soty-generator-result";
    }

}
