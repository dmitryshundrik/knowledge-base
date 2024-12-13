package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.dto.music.MusicianSelectDTO;
import java.util.List;
import java.util.stream.Collectors;

public class MusicianDTOTransformer {

    public static MusicianSelectDTO getMusicianSelectDTO(Musician musician) {
        return MusicianSelectDTO.builder()
                .id(musician.getId().toString())
                .slug(musician.getSlug())
                .nickName(musician.getNickName())
                .build();
    }

    public static List<MusicianSelectDTO> getMusicianSelectDTOList(List<Musician> musicianList) {
        return musicianList.stream().map(MusicianDTOTransformer::getMusicianSelectDTO).collect(Collectors.toList());
    }
}
