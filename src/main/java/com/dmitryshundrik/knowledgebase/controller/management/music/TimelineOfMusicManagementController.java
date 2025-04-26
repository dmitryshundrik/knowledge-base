package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.dto.core.TimelineEventDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.TimelineEvent;
import com.dmitryshundrik.knowledgebase.model.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.enums.TimelineEventType;
import com.dmitryshundrik.knowledgebase.service.core.TimelineEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static com.dmitryshundrik.knowledgebase.util.Constants.ERA_TYPE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.TIMELINE_EVENT;
import static com.dmitryshundrik.knowledgebase.util.Constants.TIMELINE_EVENT_LIST;

@Controller
@RequiredArgsConstructor
public class TimelineOfMusicManagementController {

    private final TimelineEventService timelineEventService;

    @GetMapping("/management/timeline-of-music/event/all")
    public String getAllEventsForTimelineOfMusic(Model model) {
        model.addAttribute(TIMELINE_EVENT_LIST, timelineEventService
                .getTimelineEventDTOList(timelineEventService.getAllEventsByType(TimelineEventType.MUSIC)));
        return "management/music/timeline-of-music-all";
    }

    @GetMapping("/management/timeline-of-music/event/create")
    public String getCreateEventForTimelineOfMusic(Model model) {
        model.addAttribute(TIMELINE_EVENT, new TimelineEventDto());
        model.addAttribute(ERA_TYPE_LIST, EraType.values());
        return "management/music/timeline-of-music-create";
    }

    @PostMapping("/management/timeline-of-music/event/create")
    public String postCreateEventForTimelineOfMusic(@ModelAttribute(TIMELINE_EVENT) TimelineEventDto timelineEventDTO) {
        String timelineEventId = timelineEventService.createEventForTimelineOfMusic(timelineEventDTO);
        return "redirect:/management/timeline-of-music/event/edit/" + timelineEventId;
    }

    @GetMapping("/management/timeline-of-music/event/edit/{eventId}")
    public String getEditEventForTimelineOfMusic(@PathVariable String eventId, Model model) {
        TimelineEvent timelineEventById = timelineEventService.getTimelineEventById(eventId);
        model.addAttribute(TIMELINE_EVENT, timelineEventService.getTimelineEventDTO(timelineEventById));
        model.addAttribute(ERA_TYPE_LIST, EraType.values());
        return "management/music/timeline-of-music-edit";
    }

    @PutMapping("/management/timeline-of-music/event/edit/{eventId}")
    public String putEditEventForTimelineOfMusic(@PathVariable String eventId,
                                                 @ModelAttribute(TIMELINE_EVENT) TimelineEventDto timelineEventDto) {
        timelineEventDto.setTimelineEventType(TimelineEventType.MUSIC);
        String timelineEventId = timelineEventService.updateTimelineEvent(eventId, timelineEventDto).getId();
        return "redirect:/management/timeline-of-music/event/edit/" + timelineEventId;
    }

    @DeleteMapping("/management/timeline-of-music/event/delete/{eventId}")
    public String deleteEventFromTimelineOfMusic(@PathVariable String eventId) {
        timelineEventService.deleteTimelineEvent(eventId);
        return "redirect:/management/timeline-of-music/event/all";
    }
}
