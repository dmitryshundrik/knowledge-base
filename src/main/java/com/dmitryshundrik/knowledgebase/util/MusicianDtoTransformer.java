package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSelectDto;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MusicianDtoTransformer {

    public static MusicianSelectDto getMusicianSelectDto(Musician musician) {
        return MusicianSelectDto.builder()
                .id(musician.getId().toString())
                .slug(musician.getSlug())
                .nickName(musician.getNickName())
                .build();
    }

    public static List<MusicianSelectDto> getMusicianSelectDtoList(List<Musician> musicianList) {
        return musicianList.stream().map(MusicianDtoTransformer::getMusicianSelectDto).collect(Collectors.toList());
    }
}
