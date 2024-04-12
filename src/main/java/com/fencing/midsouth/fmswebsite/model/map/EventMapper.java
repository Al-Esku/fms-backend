package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class EventMapper {
    public static Event map(EventForm form) throws DateTimeParseException {
        return new Event(form.getName(),
                form.getDescription(),
                form.getType(),
                form.getLocation(),
                form.isRegistrationRequired(),
                form.getRegistrationLink(),
                form.getResultsLink(),
                ZonedDateTime.parse(form.getStartDate()),
                ZonedDateTime.parse(form.getEndDate()));
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

    public static Event patch(Event event, EventForm form) throws DateTimeParseException {
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
            event.setStartDate(ZonedDateTime.parse(form.getStartDate()));
        }

        if (form.getEndDate() != null) {
            event.setEndDate(ZonedDateTime.parse(form.getEndDate()));
        }

        event.setLocation(form.getLocation());

        return event;
    }

}
