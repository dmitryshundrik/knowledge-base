package com.dmitryshundrik.knowledgebase.service.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicianValidationService {

    @Autowired
    private MusicianService musicianService;

    public String musicianSlugIsExist(String musicianslug) {
        String message = "";
        if (musicianService.getMusicianBySlug(musicianslug) != null) {
            message = "slug is already exist";
        }
        return message;
    }
}
