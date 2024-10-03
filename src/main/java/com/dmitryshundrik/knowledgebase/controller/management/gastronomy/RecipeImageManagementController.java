package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class RecipeImageManagementController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    public RecipeImageManagementController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}/image/create")
    public String getRecipeImageCreate(Model model, @PathVariable String recipeSlug) {
        ImageDTO imageDTO = new ImageDTO();
        model.addAttribute("imageDTO", imageDTO);
        model.addAttribute("recipeSlug", recipeSlug);
        return "management/gastronomy/recipe-image-create";
    }

    @PostMapping("/management/recipe/edit/{recipeSlug}/image/create")
    public String postRecipeImageCreate(@ModelAttribute("imageDTO") ImageDTO imageDTO, @PathVariable String recipeSlug) {
        Recipe recipeBySlug = recipeService.getBySlug(recipeSlug);
        String imageDTOSlug = imageService.createRecipeImage(imageDTO, recipeBySlug).getSlug();
        return "redirect:/management/recipe/edit/" + recipeSlug + "/image/edit/" + imageDTOSlug;
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}/image/edit/{imageSlug}")
    public String getRecipeImageEdit(@PathVariable String imageSlug, Model model, @PathVariable String recipeSlug) {
        Image bySlug = imageService.getBySlug(imageSlug);
        ImageDTO imageDTO = imageService.getImageDTO(bySlug);
        model.addAttribute("imageDTO", imageDTO);
        model.addAttribute("recipeSlug", recipeSlug);
        return "management/gastronomy/recipe-image-edit";
    }

    @PutMapping("/management/recipe/edit/{recipeSlug}/image/edit/{imageSlug}")
    public String postRecipeImageEdit(@PathVariable String imageSlug, @ModelAttribute("imageDTO") ImageDTO imageDTO,
                                      @PathVariable String recipeSlug) {
        String imageDTOSlug = imageService.updateImage(imageSlug, imageDTO).getSlug();
        return "redirect:/management/recipe/edit/" + recipeSlug + "/image/edit/" + imageDTOSlug;
    }

    @DeleteMapping("/management/recipe/edit/{recipeSlug}/image/delete/{imageSlug}")
    public String deleteRecipeImage(@PathVariable String imageSlug, @PathVariable String recipeSlug) {
        Recipe recipeBySlug = recipeService.getBySlug(recipeSlug);
        imageService.deleteRecipeImage(imageSlug, recipeBySlug);
        return "redirect:/management/recipe/edit/" + recipeSlug;
    }

    @PostMapping("/management/recipe/edit/{recipeSlug}/image/edit/{imageSlug}/file/upload")
    public String postRecipeImageEditeFileUpload(@PathVariable String imageSlug, @RequestParam("file") MultipartFile file,
                                                 @PathVariable String recipeSlug) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        imageService.uploadFile(imageSlug, bytes);
        return "redirect:/management/recipe/edit/" + recipeSlug + "/image/edit/" + imageSlug;
    }

    @DeleteMapping("/management/recipe/edit/{recipeSlug}/image/edit/{imageSlug}/file/delete")
    public String deleteRecipeImageEditFileDelete(@PathVariable String imageSlug, @PathVariable String recipeSlug) {
        imageService.deleteFile(imageSlug);
        return "redirect:/management/recipe/edit/" + recipeSlug + "/image/edit/" + imageSlug;
    }
}
