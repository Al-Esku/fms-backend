package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;

public class EventMapper {
    public static Event map(EventForm form) {
        return new Event(form.getName(),
                form.getDescription(),
                form.getType(),
                form.isRegistrationRequired(),
                form.getRegistrationLink(),
                form.getResultsLink(),
                form.getStartDate(),
                form.getEndDate());
    }

    public static EventResponse responseMap(Event event) {
        return new EventResponse(event.getName(),
                event.getUuid(),
                event.getUser().getUsername(),
                event.getDescription(),
                event.getType(),
                event.getRegistrationRequired(),
                event.getRegistrationLink(),
                event.getResultsLink(),
                event.getStartDate(),
                event.getEndDate());
    }
}
