package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;

public class EventMapper {
    public static Event map(EventForm form) {
        return new Event(form.getName(),
                form.getDescription(),
                form.getType(),
                form.getLocation(),
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
                event.getEndDate(),
                event.getLocation());
    }

    public static Event patch(Event event, EventForm form) {
        if (form.getName() != null && !form.getName().isBlank()) {
            event.setName(form.getName().trim());
        }

        if (form.getDescription() != null && !form.getDescription().isBlank()) {
            event.setDescription(form.getDescription());
        }

        if (form.getType() != null && !form.getType().isBlank()) {
            event.setType(form.getType());
        }

        event.setRegistrationRequired(form.isRegistrationRequired());

        if (form.getRegistrationLink() != null) {
            event.setRegistrationLink(form.getRegistrationLink());
        }

        if (form.getResultsLink() != null) {
            event.setResultsLink(form.getResultsLink());
        }

        if (form.getStartDate() != null) {
            event.setStartDate(form.getStartDate());
        }

        if (form.getEndDate() != null) {
            event.setEndDate(form.getEndDate());
        }

        event.setLocation(form.getLocation());

        return event;
    }

}
