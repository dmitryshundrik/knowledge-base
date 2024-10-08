package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.common.CurrentEventInfo;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CurrentEventService {

    private final MusicianService musicianService;

    private final WriterService writerService;

    private final ArtistService artistService;

    private final static String MALE_BORN = " родился ";

    private final static String FEMALE_BORN = " родилась ";

    private final static String MALE_DIE = " умер ";

    private final static String FEMALE_DIE = " умерла ";

    public CurrentEventService(MusicianService musicianService, WriterService writerService, ArtistService artistService) {
        this.musicianService = musicianService;
        this.writerService = writerService;
        this.artistService = artistService;
    }


    public List<CurrentEventInfo> getCurrentEvents() {
        List<CurrentEventInfo> currentEventInfoList = new ArrayList<>();
        currentEventInfoList.addAll(getMusicianEvents());
        currentEventInfoList.addAll(getWriterEvents());
        currentEventInfoList.addAll(getArtistEvents());
        return currentEventInfoList.stream()
                .sorted(Comparator.comparing(CurrentEventInfo::getMonth).thenComparing(CurrentEventInfo::getDay))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CurrentEventInfo> getMusicianEvents() {
        List<CurrentEventInfo> musicianEventInfoList = new ArrayList<>();
        Set<Musician> musicianBirthList = musicianService.getAllWithCurrentBirth();
        Set<Musician> musicianDeathList = musicianService.getAllWithCurrentDeath();
        for (Musician musician : musicianBirthList) {
            musicianEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(musician.getNickName())
                    .personLink("/music/musician/" + musician.getSlug())
                    .date(getDateForCurrentEvent(musician.getBirthDate()))
                    .month(musician.getBirthDate().getMonthValue())
                    .day(musician.getBirthDate().getDayOfMonth())
                    .dateType(getBirthTypeForGender(musician.getGender()))
                    .occupation(musician.getOccupation()).build());
        }
        for (Musician musician : musicianDeathList) {
            musicianEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(musician.getNickName())
                    .personLink("/music/musician/" + musician.getSlug())
                    .date(getDateForCurrentEvent(musician.getDeathDate()))
                    .month(musician.getDeathDate().getMonthValue())
                    .day(musician.getDeathDate().getDayOfMonth())
                    .dateType(getDeathTypeForGender(musician.getGender()))
                    .occupation(musician.getOccupation()).build());
        }
        return musicianEventInfoList;
    }

    @Transactional(readOnly = true)
    public List<CurrentEventInfo> getWriterEvents() {
        List<CurrentEventInfo> writerEventInfoList = new ArrayList<>();
        Set<Writer> writerBirthList = writerService.getAllWithCurrentBirth();
        Set<Writer> writerDeathList = writerService.getAllWithCurrentDeath();
        for (Writer writer : writerBirthList) {
            writerEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(writer.getNickName())
                    .personLink("/literature/writer/" + writer.getSlug())
                    .date(getDateForCurrentEvent(writer.getBirthDate()))
                    .month(writer.getBirthDate().getMonthValue())
                    .day(writer.getBirthDate().getDayOfMonth())
                    .dateType(getBirthTypeForGender(writer.getGender()))
                    .occupation(writer.getOccupation()).build());
        }
        for (Writer writer : writerDeathList) {
            writerEventInfoList.add(CurrentEventInfo.builder()
                    .personNickname(writer.getNickName())
                    .personLink("/literature/writer/" + writer.getSlug())
                    .date(getDateForCurrentEvent(writer.getDeathDate()))
                    .month(writer.getDeathDate().getMonthValue())
                    .day(writer.getDeathDate().getDayOfMonth())
                    .dateType(getDeathTypeForGender(writer.getGender()))
                    .occupation(writer.getOccupation()).build());
        }
        return writerEventInfoList;
    }

    @Transactional(readOnly = true)
    public List<CurrentEventInfo> getArtistEvents() {
        List<CurrentEventInfo> artistEventInfoList = new ArrayList<>();
        Set<Artist> artistBirthList = artistService.getAllWithCurrentBirth();
        Set<Artist> artistDeathList = artistService.getAllWithCurrentDeath();
        for (Artist artist : artistBirthList) {
            artistEventInfoList.add(CurrentEventInfo.builder()
                    .personLink("/art/artist/" + artist.getSlug())
                    .personNickname(artist.getNickName())
                    .personImage(artist.getImage())
                    .date(getDateForCurrentEvent(artist.getBirthDate()))
                    .month(artist.getBirthDate().getMonthValue())
                    .day(artist.getBirthDate().getDayOfMonth())
                    .dateType(getBirthTypeForGender(artist.getGender()))
                    .occupation(artist.getOccupation()).build());
        }
        for (Artist artist : artistDeathList) {
            artistEventInfoList.add(CurrentEventInfo.builder()
                    .personLink("/art/artist/" + artist.getSlug())
                    .personNickname(artist.getNickName())
                    .personImage(artist.getImage())
                    .date(getDateForCurrentEvent(artist.getDeathDate()))
                    .month(artist.getDeathDate().getMonthValue())
                    .day(artist.getDeathDate().getDayOfMonth())
                    .dateType(getDeathTypeForGender(artist.getGender()))
                    .occupation(artist.getOccupation()).build());
        }
        return artistEventInfoList;
    }

    public String getBirthTypeForGender(Gender gender) {
        String birthType = "";
        if (gender == Gender.FEMALE) {
            birthType = FEMALE_BORN;
        } else {
            birthType = MALE_BORN;
        }
        return birthType;
    }

    public String getDeathTypeForGender(Gender gender) {
        String deathType = "";
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
        String dateMonth = "";
        switch (dateMM) {
            case "01":
                dateMonth = "января";
                break;
            case "02":
                dateMonth = "февраля";
                break;
            case "03":
                dateMonth = "марта";
                break;
            case "04":
                dateMonth = "апреля";
                break;
            case "05":
                dateMonth = "мая";
                break;
            case "06":
                dateMonth = "июня";
                break;
            case "07":
                dateMonth = "июля";
                break;
            case "08":
                dateMonth = "августа";
                break;
            case "09":
                dateMonth = "сентября";
                break;
            case "10":
                dateMonth = "октября";
                break;
            case "11":
                dateMonth = "ноября";
                break;
            case "12":
                dateMonth = "декабря";
                break;
        }
        return dateDD + " " + dateMonth + " " + dateYYYY;
    }
}
