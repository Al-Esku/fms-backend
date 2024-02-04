package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class EventService {
    private final EventRepository eventRepository;

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getPastEvents(String name) {
        return eventRepository.findPastEvents(name);
    }

    public Event addEvent(Event event) {
        logger.info("Saving event to database");
        return eventRepository.save(event);
    }

    public List<Event> getEventsByMonth(int year, int month) {
        ZonedDateTime firstDate;
        if (month != 1) {
            firstDate = ZonedDateTime.of(year,
                    month - 1,
                    1,
                    0,
                    0,
                    0,
                    0,
                    ZoneId.of("Pacific/Auckland")).withZoneSameInstant(ZoneId.of("UTC"));
        } else {
            firstDate = ZonedDateTime.of(year - 1,
                    12,
                    1,
                    0,
                    0,
                    0,
                    0,
                    ZoneId.of("Pacific/Auckland")).withZoneSameInstant(ZoneId.of("UTC"));
        }
        ZonedDateTime lastDate;
        if (month <= 10) {
            lastDate = ZonedDateTime.of(year,
                    month + 2,
                    1,
                    0,
                    0,
                    0,
                    0,
                    ZoneId.of("Pacific/Auckland")).withZoneSameInstant(ZoneId.of("UTC"));
        } else {
            lastDate = ZonedDateTime.of(year + 1,
                    month - 10,
                    1,
                    0,
                    0,
                    0,
                    0,
                    ZoneId.of("Pacific/Auckland")).withZoneSameInstant(ZoneId.of("UTC"));
        }

        return eventRepository.findEventsBetweenDates(firstDate,
                lastDate);
    }

    public List<Event> getUpcomingEvents(String name) {
        return eventRepository.findUpcomingEvents(name);
    }
}
