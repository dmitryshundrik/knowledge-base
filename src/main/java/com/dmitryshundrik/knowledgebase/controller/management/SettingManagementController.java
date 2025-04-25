package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.entity.core.Setting;
import com.dmitryshundrik.knowledgebase.model.dto.core.SettingCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.core.SettingViewDto;
import com.dmitryshundrik.knowledgebase.service.core.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SettingManagementController {

    private final SettingService settingService;

    @GetMapping("/management/setting/all")
    public String getAllSettings(Model model) {
        List<Setting> settingList = settingService.getAll();
        List<SettingViewDto> settingViewDtoList = settingService.getSettingViewDTOList(settingList);
        model.addAttribute("settingList", settingViewDtoList);
        return "management/setting-archive";
    }

    @GetMapping("/management/setting/edit/{settingId}")
    public String getSettingEdit(@PathVariable String settingId, Model model) {
        Setting byId = settingService.getById(settingId);
        SettingCreateEditDto settingCreateEditDTO = settingService.getSettingCreateEditDTO(byId);
        model.addAttribute("settingDTO", settingCreateEditDTO);
        return "management/setting-edit";
    }

    @PutMapping("/management/setting/edit/{settingId}")
    public String putSettingEdit(@PathVariable String settingId,
                                 @ModelAttribute("settingDTO") SettingCreateEditDto settingDTO) {
        SettingViewDto settingViewDTO = settingService.updateSetting(settingId, settingDTO);
        return "redirect:/management/setting/edit/" + settingViewDTO.getId();
    }

    @DeleteMapping("/management/setting/delete/{settingId}")
    public String deleteSettingById(@PathVariable String settingId) {
        settingService.deleteSettingById(settingId);
        return "redirect:/management/setting/all";
    }
}
