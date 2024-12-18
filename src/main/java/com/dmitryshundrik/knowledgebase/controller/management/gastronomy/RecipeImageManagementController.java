package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.entity.common.Image;
import com.dmitryshundrik.knowledgebase.dto.common.ImageDTO;
import com.dmitryshundrik.knowledgebase.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.IMAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.RECIPE_SLUG;

@Controller
@RequiredArgsConstructor
public class RecipeImageManagementController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    @GetMapping("/management/recipe/edit/{recipeSlug}/image/create")
    public String getRecipeImageCreate(Model model, @PathVariable String recipeSlug) {
        ImageDTO imageDTO = new ImageDTO();
        model.addAttribute(IMAGE, imageDTO);
        model.addAttribute(RECIPE_SLUG, recipeSlug);
        return "management/gastronomy/recipe-image-create";
    }

    @PostMapping("/management/recipe/edit/{recipeSlug}/image/create")
    public String postRecipeImageCreate(@ModelAttribute(IMAGE) ImageDTO imageDTO, @PathVariable String recipeSlug) {
        Recipe recipeBySlug = recipeService.getBySlug(recipeSlug);
        String imageDTOSlug = imageService.createRecipeImage(imageDTO, recipeBySlug).getSlug();
        return "redirect:/management/recipe/edit/" + recipeSlug + "/image/edit/" + imageDTOSlug;
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}/image/edit/{imageSlug}")
    public String getRecipeImageEdit(@PathVariable String imageSlug, Model model, @PathVariable String recipeSlug) {
        Image bySlug = imageService.getBySlug(imageSlug);
        ImageDTO imageDTO = imageService.getImageDTO(bySlug);
        model.addAttribute(IMAGE, imageDTO);
        model.addAttribute(RECIPE_SLUG, recipeSlug);
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
