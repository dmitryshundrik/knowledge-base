package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.CurrentEventInfo;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WriterService writerService;


    public List<CurrentEventInfo> getCurrentEvents() {
        List<CurrentEventInfo> currentEventInfoList = new ArrayList<>(getWriterEvents());
        return currentEventInfoList;
    }

    public List<CurrentEventInfo> getWriterEvents() {
        List<CurrentEventInfo> writerEventInfoList = new ArrayList<>();
        Set<Writer> writerBirthList = writerService.getAllWithCurrentBirth();
        Set<Writer> writerDeathList = writerService.getAllWithCurrentDeath();
        for (Writer writer : writerBirthList) {
            writerEventInfoList.add(CurrentEventInfo.builder()
                    .personLink("/literature/writer/" + writer.getSlug())
                    .personNickname(writer.getNickName())
                    .personImage(writer.getImage())
                    .date(getDateForCurrentEvent(writer.getBirthDate()))
                    .dateType(" родился ")
                    .occupation(writer.getOccupation()).build());
        }
        for (Writer writer : writerDeathList) {
            writerEventInfoList.add(CurrentEventInfo.builder()
                    .personLink("/literature/writer/" + writer.getSlug())
                    .personNickname(writer.getNickName())
                    .personImage(writer.getImage())
                    .date(getDateForCurrentEvent(writer.getDeathDate()))
                    .dateType(" умер ")
                    .occupation(writer.getOccupation()).build());
        }
        return writerEventInfoList.stream()
                .sorted(Comparator.comparing(CurrentEventInfo::getDate)).collect(Collectors.toList());
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
