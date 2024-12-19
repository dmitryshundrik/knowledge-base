package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.dto.music.MusicianSelectDTO;
import java.util.List;
import java.util.stream.Collectors;

public class MusicianDtoTransformer {

    public static MusicianSelectDTO getMusicianSelectDto(Musician musician) {
        return MusicianSelectDTO.builder()
                .id(musician.getId().toString())
                .slug(musician.getSlug())
                .nickName(musician.getNickName())
                .build();
    }

    public static List<MusicianSelectDTO> getMusicianSelectDtoList(List<Musician> musicianList) {
        return musicianList.stream().map(MusicianDtoTransformer::getMusicianSelectDto).collect(Collectors.toList());
    }
}
