package com.dmitryshundrik.knowledgebase.service.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicianValidationService {

    @Autowired
    private MusicianService musicianService;

    public String musicianSlugIsExist(String musicianSlug) {
        String message = "";
        if (musicianService.getMusicianBySlug(musicianSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

}

