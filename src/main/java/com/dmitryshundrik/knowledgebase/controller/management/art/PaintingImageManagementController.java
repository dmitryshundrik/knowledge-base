package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class PaintingImageManagementController {

    private final ImageService imageService;

    private final PaintingService paintingService;

    public PaintingImageManagementController(ImageService imageService, PaintingService paintingService) {
        this.imageService = imageService;
        this.paintingService = paintingService;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/create")
    public String getPaintingImageCreate(Model model, @PathVariable String artistSlug, @PathVariable String paintingSlug) {
        ImageDTO imageDTO = new ImageDTO();
        Painting paintingBySlug = paintingService.getBySlug(paintingSlug);
        imageDTO.setSlug(paintingBySlug.getSlug());
        imageDTO.setTitle(paintingBySlug.getTitle());
        model.addAttribute("imageDTO", imageDTO);
        model.addAttribute("artistSlug", artistSlug);
        model.addAttribute("paintingSlug", paintingSlug);
        return "management/art/painting-image-create";
    }

    @PostMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/create")
    public String postPaintingImageCreate(@PathVariable String artistSlug,
                                          @PathVariable String paintingSlug,
                                          @ModelAttribute("imageDTO") ImageDTO imageDTO) {
        Painting paintingBySlug = paintingService.getBySlug(paintingSlug);
        String imageDTOSlug = imageService.createPaintingImage(imageDTO, paintingBySlug).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug + "/image/edit/" + imageDTOSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/edit/{imageSlug}")
    public String getPaintingImageEdit(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                       @PathVariable String imageSlug, Model model) {
        Image bySlug = imageService.getBySlug(imageSlug);
        ImageDTO imageDTO = imageService.getImageDTO(bySlug);
        model.addAttribute("imageDTO", imageDTO);
        model.addAttribute("artistSlug", artistSlug);
        model.addAttribute("paintingSlug", paintingSlug);
        return "management/art/painting-image-edit";
    }

    @PutMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/edit/{imageSlug}")
    public String postPaintingImageEdit(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                        @PathVariable String imageSlug, @ModelAttribute("imageDTO") ImageDTO imageDTO) {
        String imageDTOSlug = imageService.updateImage(imageSlug, imageDTO).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug + "/image/edit/" + imageDTOSlug;
    }

    @DeleteMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/delete/{imageSlug}")
    public String deleteRecipeImage(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                    @PathVariable String imageSlug) {
        Painting paintingBySlug = paintingService.getBySlug(paintingSlug);
        imageService.deletePaintingImage(imageSlug, paintingBySlug);
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug;
    }

    @PostMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/edit/{imageSlug}/file/upload")
    public String postPaintingImageEditeFileUpload(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                                   @PathVariable String imageSlug,
                                                   @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        imageService.uploadFile(imageSlug, bytes);
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug + "/image/edit/" + imageSlug;
    }

    @DeleteMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/edit/{imageSlug}/file/delete")
    public String deletePaintingImageEditFileDelete(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                                    @PathVariable String imageSlug) {
        imageService.deleteFile(imageSlug);
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug + "/image/edit/" + imageSlug;
    }
}
