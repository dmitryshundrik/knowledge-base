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
        List<SettingViewDto> settingDtoList = settingService.getSettingViewDtoList(settingList);
        model.addAttribute("settingList", settingDtoList);
        return "management/setting-archive";
    }

    @GetMapping("/management/setting/edit/{settingId}")
    public String getSettingEdit(@PathVariable String settingId, Model model) {
        Setting setting = settingService.getById(settingId);
        SettingCreateEditDto settingDto= settingService.getSettingCreateEditDto(setting);
        model.addAttribute("settingDto", settingDto);
        return "management/setting-edit";
    }

    @PutMapping("/management/setting/edit/{settingId}")
    public String putSettingEdit(@PathVariable String settingId,
                                 @ModelAttribute("settingDto") SettingCreateEditDto settingDto) {
        String settingDtoId = settingService.updateSetting(settingId, settingDto).getId();
        return "redirect:/management/setting/edit/" + settingDtoId;
    }

    @DeleteMapping("/management/setting/delete/{settingId}")
    public String deleteSettingById(@PathVariable String settingId) {
        settingService.deleteSetting(settingId);
        return "redirect:/management/setting/all";
    }
}
