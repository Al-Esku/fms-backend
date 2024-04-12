package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Event> getPastEvents(String name, int pageNumber, int pageSize, String[] types, String[] creators) {
        return eventRepository.findPastEvents(name, PageRequest.of(pageNumber, pageSize), types, creators);
    }

    public void addEvent(Event event) {
        logger.info("Saving event to database");
        eventRepository.save(event);
    }

    public Event getEventFromUuid(String uuid) {
        return eventRepository.findEventByUuid(uuid);
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

    public List<Event> getTop2UpcomingEventsByClub(Club club) {
        Sort.Order sort = new Sort.Order(Sort.Direction.ASC, "startDate");
        return eventRepository.findTop2ByStartDateAfterAndUser(ZonedDateTime.now(), club.getUser(), Sort.by(sort));
    }

    public Page<Event> getUpcomingEvents(String name, int pageNumber, int pageSize, String[] types, String[] creators) {
        return eventRepository.findUpcomingEvents(name, PageRequest.of(pageNumber, pageSize), types, creators);
    }

    public Page<Event> getEventsBeforeDate(String name, int pageNumber, int pageSize, String[] types, String[] creators, ZonedDateTime date) {
        return eventRepository.findEventsBeforeDate(name, PageRequest.of(pageNumber, pageSize), types, creators, date);
    }

    public Page<Event> getEventsAfterDate(String name, int pageNumber, int pageSize, String[] types, String[] creators, ZonedDateTime date) {
        return eventRepository.findEventsAfterDate(name, PageRequest.of(pageNumber, pageSize), types, creators, date);
    }

    public Page<Event> getEventsBetweenDates(String name, int pageNumber, int pageSize, String[] types, String[] creators, ZonedDateTime before, ZonedDateTime after) {
        return eventRepository.findEventsBetweenDates(name, PageRequest.of(pageNumber, pageSize), types, creators, before, after);
    }

    @Transactional
    public void deleteEventByUuid(String uuid) {
        eventRepository.deleteEventByUuid(uuid);
    }

    public Event updateEvent(Event event) throws ObjectNotFoundException {
        if (eventRepository.existsByUuid(event.getUuid().toString())) {
            return eventRepository.save(event);
        }
        throw new ObjectNotFoundException(event.getUuid(), event.getName());
    }
}
