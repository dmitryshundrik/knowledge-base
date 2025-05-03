package com.dmitryshundrik.knowledgebase.controller.management.tools;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.tools.Soty;
import com.dmitryshundrik.knowledgebase.model.entity.tools.SotyPair;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionSotyGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SotyGeneratorManagementController {

    private final CompositionService compositionService;

    private final CompositionSotyGeneratorService compositionSotyGeneratorService;

    private static List<SotyPair> staticPairList = new ArrayList<>();

    private static List<Map.Entry<Composition, Double>> result = new ArrayList<>();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1000);
    }

    @GetMapping("/management/soty-generator")
    public String getSotyGeneratorYear(Model model) {
        List<Integer> years = compositionService.getAllYearsFromCompositions();
        model.addAttribute("years", years);
        model.addAttribute("soty", new Soty());
        return "management/tools/soty-generator-year";
    }

    @PostMapping("/management/soty-generator")
    public String postSotyGenerator(@ModelAttribute("soty") Soty soty) {
        if (soty.getYear() == null) {
            return "redirect:/management/soty-generator";
        }
        return "redirect:/management/soty-generator/generate/" + soty.getYear();
    }

    @GetMapping(("/management/soty-generator/generate/{year}"))
    public String getSotyGeneratorGenerate(@PathVariable Integer year, Model model) {
        staticPairList = compositionSotyGeneratorService.getPairListForSotyGenerator(year);
        Soty soty = new Soty();
        soty.setPairList(staticPairList);
        soty.setYear(year);
        model.addAttribute("soty", soty);
        return "management/tools/soty-generator-generate";
    }

    @PostMapping("/management/soty-generator/generate/{year}")
    public String postSotyGeneratorGenerate(@ModelAttribute("soty") Soty soty, @PathVariable String year) {
        result = compositionSotyGeneratorService.getTopForSotyGenerator(staticPairList, soty.getPairList());
        return "redirect:/management/soty-generator/generate/" + year + "/result";
    }

    @GetMapping("/management/soty-generator/generate/{year}/result")
    public String getSotyGeneratorResult(Model model, @PathVariable String year) {
        model.addAttribute("result", result);
        model.addAttribute("year", year);
        return "management/tools/soty-generator-result";
    }
}
