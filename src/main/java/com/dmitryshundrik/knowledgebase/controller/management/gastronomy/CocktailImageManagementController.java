package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.model.entity.common.Image;
import com.dmitryshundrik.knowledgebase.model.dto.common.ImageDTO;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import lombok.RequiredArgsConstructor;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.COCKTAIL_SLUG;
import static com.dmitryshundrik.knowledgebase.util.Constants.IMAGE;

@Controller
@RequiredArgsConstructor
public class CocktailImageManagementController {

    private final ImageService imageService;

    private final CocktailService cocktailService;

    @GetMapping("/management/cocktail/edit/{cocktailSlug}/image/create")
    public String getRecipeImageCreate(Model model, @PathVariable String cocktailSlug) {
        ImageDTO imageDTO = new ImageDTO();
        model.addAttribute(IMAGE, imageDTO);
        model.addAttribute(COCKTAIL_SLUG, cocktailSlug);
        return "management/gastronomy/cocktail-image-create";
    }

    @PostMapping("/management/cocktail/edit/{cocktailSlug}/image/create")
    public String postRecipeImageCreate(@ModelAttribute(IMAGE) ImageDTO imageDTO, @PathVariable String cocktailSlug) {
        Cocktail cocktailBySlug = cocktailService.getBySlug(cocktailSlug);
        String imageDTOSlug = imageService.createCocktailImage(imageDTO, cocktailBySlug).getSlug();
        return "redirect:/management/cocktail/edit/" + cocktailSlug + "/image/edit/" + imageDTOSlug;
    }

    @GetMapping("/management/cocktail/edit/{cocktailSlug}/image/edit/{imageSlug}")
    public String getRecipeImageEdit(@PathVariable String imageSlug, Model model, @PathVariable String cocktailSlug) {
        Image bySlug = imageService.getBySlug(imageSlug);
        ImageDTO imageDTO = imageService.getImageDTO(bySlug);
        model.addAttribute(IMAGE, imageDTO);
        model.addAttribute(COCKTAIL_SLUG, cocktailSlug);
        return "management/gastronomy/cocktail-image-edit";
    }

    @PutMapping("/management/cocktail/edit/{cocktailSlug}/image/edit/{imageSlug}")
    public String postRecipeImageEdit(@PathVariable String imageSlug, @ModelAttribute(IMAGE) ImageDTO imageDTO,
                                      @PathVariable String cocktailSlug) {
        String imageDTOSlug = imageService.updateImage(imageSlug, imageDTO).getSlug();
        return "redirect:/management/cocktail/edit/" + cocktailSlug + "/image/edit/" + imageDTOSlug;
    }

    @DeleteMapping("/management/cocktail/edit/{cocktailSlug}/image/delete/{imageSlug}")
    public String deleteRecipeImage(@PathVariable String imageSlug, @PathVariable String cocktailSlug) {
        Cocktail cocktailBySlug = cocktailService.getBySlug(cocktailSlug);
        imageService.deleteCocktailImage(imageSlug, cocktailBySlug);
        return "redirect:/management/cocktail/edit/" + cocktailSlug;
    }

    @PostMapping("/management/cocktail/edit/{cocktailSlug}/image/edit/{imageSlug}/file/upload")
    public String postRecipeImageEditeFileUpload(@PathVariable String imageSlug, @RequestParam("file") MultipartFile file,
                                                 @PathVariable String cocktailSlug) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        imageService.uploadFile(imageSlug, bytes);
        return "redirect:/management/cocktail/edit/" + cocktailSlug + "/image/edit/" + imageSlug;
    }

    @DeleteMapping("/management/cocktail/edit/{cocktailSlug}/image/edit/{imageSlug}/file/delete")
    public String deleteRecipeImageEditFileDelete(@PathVariable String imageSlug, @PathVariable String cocktailSlug) {
        imageService.deleteFile(imageSlug);
        return "redirect:/management/cocktail/edit/" + cocktailSlug + "/image/edit/" + imageSlug;
    }
}
