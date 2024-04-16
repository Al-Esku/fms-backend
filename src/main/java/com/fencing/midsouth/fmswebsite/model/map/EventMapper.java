package com.fencing.midsouth.fmswebsite.model.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.Location;
import com.google.gson.Gson;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class EventMapper {
    public static Event map(EventForm form) throws DateTimeParseException {
        Location location;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            location = objectMapper.readValue(form.getLocation(), Location.class);
        } catch (JsonProcessingException e) {
            location = null;
        }
        return new Event(form.getName(),
                form.getDescription(),
                form.getType(),
                location,
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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            event.setLocation(objectMapper.readValue(form.getLocation(), Location.class));
        } catch (JsonProcessingException e) {
            event.setLocation(null);
        }

        return event;
    }

}
