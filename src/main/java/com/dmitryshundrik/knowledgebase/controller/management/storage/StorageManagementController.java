package com.dmitryshundrik.knowledgebase.controller.management.storage;

import com.dmitryshundrik.knowledgebase.model.entity.common.Image;
import com.dmitryshundrik.knowledgebase.model.dto.common.ImageDTO;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StorageManagementController {

    private final ImageService imageService;

    @GetMapping("/management/storage/image/all")
    public String getAllImages(Model model) {
        List<Image> imageList = imageService.getAllSortedByCreatedDesc();
        List<ImageDTO> imageDTOList = imageService.getImageDTOList(imageList);
        model.addAttribute("imageDTOList", imageDTOList);
        return "management/storage/image-all";
    }

    @GetMapping("/storage/images/{imageSlug}")
    public ResponseEntity<byte[]> getImageFromArchive(@PathVariable String imageSlug) {
        String base64Image = imageService.getBySlug(imageSlug).getData();
        byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
}
