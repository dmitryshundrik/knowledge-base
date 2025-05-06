package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.dto.core.ImageDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.entity.core.Image;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.core.ImageService;
import lombok.RequiredArgsConstructor;
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
import java.util.Base64;

import static com.dmitryshundrik.knowledgebase.util.Constants.ARTIST_SLUG;
import static com.dmitryshundrik.knowledgebase.util.Constants.IMAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.PAINTING_SLUG;

@Controller
@RequiredArgsConstructor
public class PaintingImageManagementController {

    private final ImageService imageService;

    private final PaintingService paintingService;

    @GetMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/create")
    public String getPaintingImageCreate(Model model, @PathVariable String artistSlug, @PathVariable String paintingSlug) {
        ImageDto imageDto = new ImageDto();
        Painting paintingBySlug = paintingService.getBySlug(paintingSlug);
        imageDto.setSlug(paintingBySlug.getSlug());
        imageDto.setTitle(paintingBySlug.getTitle());
        model.addAttribute(IMAGE, imageDto);
        model.addAttribute(ARTIST_SLUG, artistSlug);
        model.addAttribute(PAINTING_SLUG, paintingSlug);
        return "management/art/painting-image-create";
    }

    @PostMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/create")
    public String postPaintingImageCreate(@PathVariable String artistSlug,
                                          @PathVariable String paintingSlug,
                                          @ModelAttribute(IMAGE) ImageDto imageDto) {
        Painting paintingBySlug = paintingService.getBySlug(paintingSlug);
        String imageDtoSlug = imageService.createPaintingImage(imageDto, paintingBySlug).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug + "/image/edit/" + imageDtoSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/edit/{imageSlug}")
    public String getPaintingImageEdit(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                       @PathVariable String imageSlug, Model model) {
        Image bySlug = imageService.getBySlug(imageSlug);
        ImageDto imageDto = imageService.getImageDto(bySlug);
        model.addAttribute(IMAGE, imageDto);
        model.addAttribute(ARTIST_SLUG, artistSlug);
        model.addAttribute(PAINTING_SLUG, paintingSlug);
        return "management/art/painting-image-edit";
    }

    @PutMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}/image/edit/{imageSlug}")
    public String postPaintingImageEdit(@PathVariable String artistSlug, @PathVariable String paintingSlug,
                                        @PathVariable String imageSlug, @ModelAttribute(IMAGE) ImageDto imageDto) {
        String imageDtoSlug = imageService.updateImage(imageSlug, imageDto).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingSlug + "/image/edit/" + imageDtoSlug;
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
        byte[] bytes = Base64.getEncoder().encode(file.getBytes());
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
