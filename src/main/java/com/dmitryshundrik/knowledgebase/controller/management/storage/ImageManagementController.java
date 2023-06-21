package com.dmitryshundrik.knowledgebase.controller.management.storage;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ImageManagementController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/archive/images/{imageSlug}")
    public ResponseEntity<byte[]> getImageFromArchive(@PathVariable String imageSlug) {
        String base64Image = imageService.getBySlug(imageSlug).getData();
        byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    @GetMapping("/management/storage/image/all")
    public String getAllImages(Model model) {
        List<Image> images = imageService.getAllSortedByCreated();

        model.addAttribute("images", images);
        return "management/storage/image-all";
    }

    @GetMapping("/management/storage/image/create")
    public String getImageCreate(Model model) {
        model.addAttribute("image", new Image());
        return "management/storage/image-create";
    }

    @PostMapping("/management/storage/image/create")
    public String postUploadImage(@ModelAttribute("image") Image image) {
        Image createImage = imageService.createImage(image);
        return "redirect:/management/storage/image/edit/" + createImage.getSlug();
    }

    @GetMapping("/management/storage/image/edit/{imageSlug}")
    public String getImageEdit(@PathVariable String imageSlug, Model model) {
        Image bySlug = imageService.getBySlug(imageSlug);
        model.addAttribute("image", bySlug);
        return "management/storage/image-edit";
    }

    @PutMapping("/management/storage/image/edit/{imageSlug}")
    public String postImageEdit(@PathVariable String imageSlug,
                                @ModelAttribute("image") Image imageDTO) {
        Image updateImage = imageService.updateImage(imageSlug, imageDTO);
        return "redirect:/management/storage/image/edit/" + updateImage.getSlug();
    }

    @DeleteMapping("/management/storage/image/delete/{imageSlug}")
    public String deleteImage(@PathVariable String imageSlug) {
        imageService.deleteImage(imageSlug);
        return "redirect:/management/storage/image/all";
    }

    @PostMapping("/management/storage/image/edit/{imageSlug}/file/upload")
    public String postUploadImage(@PathVariable String imageSlug,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        imageService.uploadFile(imageSlug, bytes);
        return "redirect:/management/storage/image/edit/" + imageSlug;
    }

    @DeleteMapping("/management/storage/image/edit/{imageSlug}/file/delete")
    public String deleteFile(@PathVariable String imageSlug) {
        imageService.deleteFile(imageSlug);
        return "redirect:/management/storage/image/all";
    }

}
