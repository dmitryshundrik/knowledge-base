package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSelectDTO;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
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
