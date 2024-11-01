package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.common.Setting;
import com.dmitryshundrik.knowledgebase.model.common.dto.SettingCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.common.dto.SettingViewDTO;
import com.dmitryshundrik.knowledgebase.service.common.SettingService;
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
        List<SettingViewDTO> settingViewDTOList = settingService.getSettingViewDTOList(settingList);
        model.addAttribute("settingList", settingViewDTOList);
        return "management/setting-archive";
    }

    @GetMapping("/management/setting/edit/{settingId}")
    public String getSettingEdit(@PathVariable String settingId, Model model) {
        Setting byId = settingService.getById(settingId);
        SettingCreateEditDTO settingCreateEditDTO = settingService.getSettingCreateEditDTO(byId);
        model.addAttribute("settingDTO", settingCreateEditDTO);
        return "management/setting-edit";
    }

    @PutMapping("/management/setting/edit/{settingId}")
    public String putSettingEdit(@PathVariable String settingId,
                                 @ModelAttribute("settingDTO") SettingCreateEditDTO settingDTO) {
        SettingViewDTO settingViewDTO = settingService.updateSetting(settingId, settingDTO);
        return "redirect:/management/setting/edit/" + settingViewDTO.getId();
    }

    @DeleteMapping("/management/setting/delete/{settingId}")
    public String deleteSettingById(@PathVariable String settingId) {
        settingService.deleteSettingById(settingId);
        return "redirect:/management/setting/all";
    }
}
