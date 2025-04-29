package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.core.CurrentEventInfo;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.dmitryshundrik.knowledgebase.util.Constants.FEMALE_BORN;
import static com.dmitryshundrik.knowledgebase.util.Constants.FEMALE_DIE;
import static com.dmitryshundrik.knowledgebase.util.Constants.MALE_BORN;
import static com.dmitryshundrik.knowledgebase.util.Constants.MALE_DIE;

@Component
public class EntityEvenCreator {

    public List<CurrentEventInfo> createMusicianEvents(Set<Musician> musicianBirthList, Set<Musician> musicianDeathList) {
        List<CurrentEventInfo> musicianEventInfoList = new ArrayList<>();
        for (Musician musician : musicianBirthList) {
            musicianEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(musician.getNickName())
                    .personLink("/music/musician/" + musician.getSlug())
                    .date(getDateForCurrentEvent(musician.getBirthDate()))
                    .month(musician.getBirthDate().getMonthValue())
                    .day(musician.getBirthDate().getDayOfMonth())
                    .dateType(" " + getBirthTypeForGender(musician.getGender()) + " ")
                    .occupation(musician.getOccupation()).build());
        }
        for (Musician musician : musicianDeathList) {
            musicianEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(musician.getNickName())
                    .personLink("/music/musician/" + musician.getSlug())
                    .date(getDateForCurrentEvent(musician.getDeathDate()))
                    .month(musician.getDeathDate().getMonthValue())
                    .day(musician.getDeathDate().getDayOfMonth())
                    .dateType(" " + getDeathTypeForGender(musician.getGender()) + " ")
                    .occupation(musician.getOccupation()).build());
        }
        return musicianEventInfoList;
    }

    public List<CurrentEventInfo> createWriterEvents(Set<Writer> entityBirthList, Set<Writer> entityDeathList) {
        List<CurrentEventInfo> entityEventInfoList = new ArrayList<>();
        for (Writer writer : entityBirthList) {
            entityEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(writer.getNickName())
                    .personLink("/literature/writer/" + writer.getSlug())
                    .date(getDateForCurrentEvent(writer.getBirthDate()))
                    .month(writer.getBirthDate().getMonthValue())
                    .day(writer.getBirthDate().getDayOfMonth())
                    .dateType(" " + getBirthTypeForGender(writer.getGender()) + " ")
                    .occupation(writer.getOccupation()).build());
        }
        for (Writer writer : entityDeathList) {
            entityEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(writer.getNickName())
                    .personLink("/literature/writer/" + writer.getSlug())
                    .date(getDateForCurrentEvent(writer.getDeathDate()))
                    .month(writer.getDeathDate().getMonthValue())
                    .day(writer.getDeathDate().getDayOfMonth())
                    .dateType(" " + getDeathTypeForGender(writer.getGender()) + " ")
                    .occupation(writer.getOccupation()).build());
        }
        return entityEventInfoList;
    }

    public List<CurrentEventInfo> createArtistEvents(Set<Artist> entityBirthList, Set<Artist> entityDeathList) {
        List<CurrentEventInfo> entityEventInfoList = new ArrayList<>();
        for (Artist artist : entityBirthList) {
            entityEventInfoList.add(CurrentEventInfo.builder()
                    .personLink("/art/artist/" + artist.getSlug())
                    .personNickname(artist.getNickName())
                    .personImage(artist.getImage())
                    .date(getDateForCurrentEvent(artist.getBirthDate()))
                    .month(artist.getBirthDate().getMonthValue())
                    .day(artist.getBirthDate().getDayOfMonth())
                    .dateType(" " + getBirthTypeForGender(artist.getGender()) + " ")
                    .occupation(artist.getOccupation()).build());
        }
        for (Artist artist : entityDeathList) {
            entityEventInfoList.add(CurrentEventInfo.builder()
                    .personLink("/art/artist/" + artist.getSlug())
                    .personNickname(artist.getNickName())
                    .personImage(artist.getImage())
                    .date(getDateForCurrentEvent(artist.getDeathDate()))
                    .month(artist.getDeathDate().getMonthValue())
                    .day(artist.getDeathDate().getDayOfMonth())
                    .dateType(" " + getDeathTypeForGender(artist.getGender()) + " ")
                    .occupation(artist.getOccupation()).build());
        }
        return entityEventInfoList;
    }

    public String getBirthTypeForGender(Gender gender) {
        String birthType;
        if (gender == Gender.FEMALE) {
            birthType = FEMALE_BORN;
        } else {
            birthType = MALE_BORN;
        }
        return birthType;
    }

    public String getDeathTypeForGender(Gender gender) {
        String deathType;
        if (gender == Gender.FEMALE) {
            deathType = FEMALE_DIE;
        } else {
            deathType = MALE_DIE;
        }
        return deathType;
    }

    public String getDateForCurrentEvent(LocalDate localDate) {
        String dateDD = localDate.format(DateTimeFormatter.ofPattern("dd"));
        String dateMM = localDate.format(DateTimeFormatter.ofPattern("MM"));
        String dateYYYY = localDate.format(DateTimeFormatter.ofPattern("yyyy"));
        String dateMonth = switch (dateMM) {
            case "01" -> "января";
            case "02" -> "февраля";
            case "03" -> "марта";
            case "04" -> "апреля";
            case "05" -> "мая";
            case "06" -> "июня";
            case "07" -> "июля";
            case "08" -> "августа";
            case "09" -> "сентября";
            case "10" -> "октября";
            case "11" -> "ноября";
            case "12" -> "декабря";
            default -> "";
        };
        return dateDD + " " + dateMonth + " " + dateYYYY;
    }
}
